package com.self.springbootprojectstarter.config;

import com.self.springbootprojectstarter.DTO.CategoryDto;
import com.self.springbootprojectstarter.DTO.OrderDto;
import com.self.springbootprojectstarter.DTO.ProductDto;
import com.self.springbootprojectstarter.DTO.ProfileDto;
import com.self.springbootprojectstarter.DTO.UserDto;
import com.self.springbootprojectstarter.model.Category;
import com.self.springbootprojectstarter.model.Order;
import com.self.springbootprojectstarter.model.Product;
import com.self.springbootprojectstarter.model.Profile;
import com.self.springbootprojectstarter.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    // Centralized ModelMapper configuration used by all mappers/services.
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Strict matching avoids accidental field mapping
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // Prevent client-supplied ids from overwriting database ids.
        modelMapper.typeMap(UserDto.class, User.class)
                .addMappings(mapper -> mapper.skip(User::setId));
        modelMapper.typeMap(ProfileDto.class, Profile.class)
                .addMappings(mapper -> mapper.skip(Profile::setId));
        modelMapper.typeMap(CategoryDto.class, Category.class)
                .addMappings(mapper -> mapper.skip(Category::setId));
        modelMapper.typeMap(ProductDto.class, Product.class)
                .addMappings(mapper -> mapper.skip(Product::setId));
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        return modelMapper;
    }
}
