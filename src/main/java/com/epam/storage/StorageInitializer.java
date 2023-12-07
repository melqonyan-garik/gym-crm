package com.epam.storage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class StorageInitializer implements BeanPostProcessor {



    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StorageBean) {
            StorageBean storageBean = (StorageBean) bean;
            storageBean.initializeStorage();
        }
        return bean;
    }
}
