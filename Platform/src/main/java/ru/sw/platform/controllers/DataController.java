package ru.sw.platform.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.sw.platform.core.annotations.Action;
import ru.sw.platform.core.entity.AbstractEntity;
import ru.sw.platform.core.exceptions.PlatofrmExecption;
import ru.sw.platform.core.services.AbstractService;
import ru.sw.platform.core.utils.ModuleLocator;
import ru.sw.platform.modules.role.Role;
import ru.sw.platform.modules.user.User;
import ru.sw.platform.modules.user.UserRepository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/platform/data")
public class DataController {

    @Autowired
    private ModuleLocator moduleLocator;

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(DataController.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    String read(@RequestParam("id") Long id, @RequestParam("className") String className) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            AbstractEntity entity = moduleLocator.findRepository(classEntity).read(id, classEntity);
            if (entity == null) {
                return "";
            }
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            logger.warn("JsonException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ReadError);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ReadError);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Transactional
    public
    @ResponseBody
    String create(@RequestParam("object") String jsonObject, @RequestParam("className") String className, Principal principal) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            AbstractEntity abstractEntity = (AbstractEntity) objectMapper.readValue(jsonObject, classEntity);
            isOwner(principal, abstractEntity);
            moduleLocator.findRepository(classEntity).create(abstractEntity);

            return "";
        } catch (JsonProcessingException e) {
            logger.warn("JsonException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.CreateError);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.CreateError);
        } catch (IOException e) {
            logger.warn("IOException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.CreateError);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Transactional
    public
    @ResponseBody
    String update(@RequestParam("object") String jsonObject, @RequestParam("className") String className, Principal principal) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            AbstractEntity abstractEntity = (AbstractEntity) objectMapper.readValue(jsonObject, classEntity);
            isOwner(principal, abstractEntity);
            moduleLocator.findRepository(classEntity).update(abstractEntity);

            return "";
        } catch (JsonProcessingException e) {
            logger.warn("JsonException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.UpdateError);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.UpdateError);
        } catch (IOException e) {
            logger.warn("IOException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.UpdateError);
        }
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    String count(@RequestParam("className") String className) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            return moduleLocator.findRepository(classEntity).count(classEntity).toString();
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.CountError);
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    String remove(@RequestParam("id") Long id, @RequestParam("className") String className, Principal principal) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            String jsonObject = read(id, className);
            AbstractEntity abstractEntity = (AbstractEntity) objectMapper.readValue(jsonObject, classEntity);
            isOwner(principal, abstractEntity);
            moduleLocator.findRepository(classEntity).remove(id, classEntity);
            
            return "";
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.RemoveError);
        } catch (JsonMappingException e) {
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.RemoveError);
        } catch (JsonParseException e) {
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.RemoveError);
        } catch (IOException e) {
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.RemoveError);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    String list(@RequestParam("className") String className) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            List<AbstractEntity> list = moduleLocator.findRepository(classEntity).list(classEntity);

            if (list == null) {
                return "";
            }

            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.warn("JsonException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ListError);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ListError);
        }
    }

    @RequestMapping(value = "/listPartial", method = RequestMethod.GET)
    @Transactional
    public
    @ResponseBody
    String listPartial(@RequestParam("className") String className, @RequestParam("first") Integer first, @RequestParam("max") Integer max) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            List<AbstractEntity> list = moduleLocator.findRepository(classEntity).listPartial(classEntity, first, max);

            if (list == null) {
                return "";
            }


            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.warn("JsonException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ListPartialError);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ListPartialError);
        }
    }

    @RequestMapping(value = "/action", method = RequestMethod.POST)
    @Transactional
    public
    @ResponseBody
    String action(@RequestParam("className") String className, @RequestParam("actionName") String actionName, @RequestParam("data") String jsonData , Principal principal) throws PlatofrmExecption {
        try {
            Class classEntity = Class.forName(className);
            AbstractService abstractService = moduleLocator.findService(classEntity);

            for (Method method : abstractService.getClass().getMethods()) {
                Action action = AnnotationUtils.findAnnotation(method, Action.class);
                if(action == null) {
                    continue;
                }

                if (action.name().equals(actionName)) {
                    TypeReference<HashMap<String, Object>> typeRef
                            = new TypeReference<HashMap<String, Object>>() {
                    };

                    HashMap<String, Object> data = objectMapper.readValue(jsonData, typeRef);
                    if(principal != null) {
                        data.put("principal", principal.getName());
                    } else {
                        data.put("principal" , "");
                    }
                    Object result = method.invoke(abstractService, data);
                    return objectMapper.writeValueAsString(result);
                }
            }

            return "";
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFound", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        } catch (InvocationTargetException e) {
            logger.warn("InvocationTargetException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        } catch (IllegalAccessException e) {
            logger.warn("IllegalAccessException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        } catch (JsonMappingException e) {
            logger.warn("JsonMappingException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        } catch (JsonParseException e) {
            logger.warn("JsonParseException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        } catch (IOException e) {
            logger.warn("IOException", e);
            throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
        }
    }

    @ExceptionHandler(PlatofrmExecption.class)
    public
    @ResponseBody
    String errorHandling(PlatofrmExecption data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    private void isOwner(Principal principal, AbstractEntity abstractEntity) throws PlatofrmExecption {
        if (principal == null) {
            throw new PlatofrmExecption("Not authorized user can't update entity", PlatofrmExecption.Type.UpdateError);
        }

        User user = userRepository.getUserByLogin(principal.getName());
        if (user.getRole().getRoleName().equals("ROLE_ADMIN")) {
            return;
        }
        if (user == null) {
            throw new PlatofrmExecption("User not found", PlatofrmExecption.Type.UpdateError);
        }

        if(abstractEntity.getOwners().getRoles() != null) {
            boolean rightRole = false;
            for (Role role : abstractEntity.getOwners().getRoles()) {
                if(user.getRole().getRoleName().equals(role.getRoleName())){
                    rightRole = true;
                    break;
                }
            }
            if(!rightRole){
                throw new PlatofrmExecption("Entity can not be changed", PlatofrmExecption.Type.UpdateError);
            } else {
                return;
            }
        }

        List<User> list = abstractEntity.getOwners().getUserList();
        if (list == null) {
            throw new PlatofrmExecption("Entity can not be changed", PlatofrmExecption.Type.UpdateError);
        }


        if (!list.contains(user)) {
            throw new PlatofrmExecption("Entity can not be changed for you", PlatofrmExecption.Type.UpdateError);
        }
    }

    @RequestMapping(value = "authorizedUser", method = RequestMethod.GET)
    public User getAuthorizedUser(Principal p) {
        if(p.getName() == null) {
            return  null;
        } else {
            User user = userRepository.getUserByLogin(p.getName());
            return user;
        }
    }
}
