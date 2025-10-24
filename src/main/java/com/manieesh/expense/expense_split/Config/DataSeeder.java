package com.manieesh.expense.expense_split.Config;

import com.manieesh.expense.expense_split.Model.Groups;
import com.manieesh.expense.expense_split.Model.User;
import com.manieesh.expense.expense_split.Repository.GroupRepository;
import com.manieesh.expense.expense_split.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration


public class DataSeeder {
    @Bean
    CommandLineRunner seedData(UserRepository userRepository, GroupRepository groupRepository) {
        return args -> {

            // 1️⃣ Create a user
            User user = new User();
            user.setName("Manieesh");
            user.setEmail("manieesh@example.com");
            user.setPhone(9876543210L);
            user.setPassword("password123");
            user.setVerified(true);
            userRepository.save(user);

            // 2️⃣ Create a group with that user as creator and member
            Groups group = new Groups();
            group.setName("Friends Group");
            group.setDescription("Testing group for expenses");
            group.setCreatedBy(user);
            group.setMembers(Set.of(user));

            groupRepository.save(group);

            System.out.println("✅ Seed data inserted successfully!");
        };
    }


}
