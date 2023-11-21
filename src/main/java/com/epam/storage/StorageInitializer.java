package com.epam.storage;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class StorageInitializer implements BeanPostProcessor {


    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof StorageBean) {
            StorageBean storageBean = (StorageBean) bean;
            storageBean.initializeStorage();
        }
        return bean;
    }
}
