package com.ngts.common.func.pojo;/*
package com.ngts.projects.scm.core.api.functions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
@Slf4j
public class RolesGrpConverter implements Converter<String, RolesGroup> {

    private static final Logger logger = LoggerFactory.getLogger(RolesGrpConverter.class);

    @Override
    public RolesGroup convert(String source) {
        if(source == null)
        return null;
        else
        {
            logger.error("Role group is not null >>>>>> " + source);
            return  new RolesGroup();
        }
    }
}
*/
