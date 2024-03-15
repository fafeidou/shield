package injection;

import injection.annotation.UserGroup;
import org.geekbang.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Collection;

public class QualifierNameAnnotationDependencyInjectionDemo {

    @Autowired
    @Qualifier("user")
    private User user;

    @Autowired
    @Qualifier
    private User user_group;

    @Autowired
    private Collection<User> allUsers;

    @Autowired
    @Qualifier
    private Collection<User> allUsers_group;

    @Autowired
    @UserGroup
    private Collection<User> allUsers_group_annotation;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierNameAnnotationDependencyInjectionDemo.class);
        applicationContext.refresh();
        QualifierNameAnnotationDependencyInjectionDemo beanDemo = applicationContext.getBean(QualifierNameAnnotationDependencyInjectionDemo.class);
        System.out.println("名称：" + beanDemo.user);
        System.out.println("分组：" + beanDemo.user_group);
        System.out.println("集合：size-" + beanDemo.allUsers.size() + "=" + beanDemo.allUsers);
        System.out.println("集合-分组：size-" + beanDemo.allUsers_group.size() + "=" + beanDemo.allUsers_group);
        System.out.println("集合-分组-注解：size-" + beanDemo.allUsers_group_annotation.size() + "=" + beanDemo.allUsers_group_annotation);
        applicationContext.close();
    }

    @Bean
    public User user() {
        return createUser("bean");
    }

    @Bean
    @Qualifier
    public User userGroup() {
        return createUser("bean-group");
    }

    @Bean
    @Qualifier
    @Primary
    public User userGroup2() {
        return createUser("bean-group2");
    }

    @Bean
    @Primary
    public User superUser() {
        return createUser("super-bean");
    }

    @Bean
    @UserGroup
    public User userGroup3() {
        return createUser("bean-group-annotation1");
    }

    @Bean
    @UserGroup
    public User userGroup4() {
        return createUser("bean-group-annotation2");
    }

    private static User createUser(String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

}
