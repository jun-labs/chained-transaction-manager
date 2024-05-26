package project.io.app.core.user.domain;

public interface UserWriteRepository {
    User saveSource(User user);

    User saveTarget(User user);
}
