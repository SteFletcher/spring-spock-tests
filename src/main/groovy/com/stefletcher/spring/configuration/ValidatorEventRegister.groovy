package com.stefletcher.spring.configuration

import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener
import org.springframework.validation.Validator

/**
 * Created by ste on 13/07/17.
 */
@Configuration
class ValidatorEventRegister implements InitializingBean{

    @Autowired
    ValidatingRepositoryEventListener validatingRepositoryEventListener

    @Autowired
    Map<String, Validator> validators

    @Override
    void afterPropertiesSet() throws Exception {
        List<String> events = Arrays.asList("beforeCreate", "afterCreate", "beforeSave", "afterSave", "beforeLinkSave", "afterLinkSave", "beforeDelete", "afterDelete");
        for(Map.Entry<String, Validator> entry : validators.entrySet()){
            events.stream()
                .filter{p -> entry.getKey().startsWith(p)}
                .findFirst()
                .ifPresent{ p -> validatingRepositoryEventListener.addValidator(p, entry.getValue())}
        }
    }
}
