package com.self.springbootprojectstarter.config;

import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<User, Long> profileIdConverter = ctx ->
                ctx.getSource().getProfile() == null
                        ? null
                        : ctx.getSource().getProfile().getId();

        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper ->
                        mapper.using(profileIdConverter)
                                .map(src -> src, UserDto::setProfileId)
                );

        return modelMapper;
    }
}