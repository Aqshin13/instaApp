package com.company.service;

import com.company.dto.UserRegisterDTO;
import com.company.entities.User;
import com.company.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @TempDir
    private Path tempDir;

    @Test
    public void givenUserRegisterDto_whenSave_thenUserIsSavedCorrectly() {
//     given
        UserRegisterDTO dto = UserRegisterDTO
                .builder()
                .username("username")
                .password("password")
                .build();
//       when
        userService.save(dto);
//       then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());
    }


    @Test
    void testSaveProfileImage() throws Exception {
        User user = new User();
        user.setImageUrl("old-image.jpg");
        byte[] dummyData = "test image content".getBytes();
        MultipartFile mockFile = new MockMultipartFile(
                "image",
                "image.jpg",
                "image/jpeg",
                dummyData
        );
        Field uploadDirField = UserService.class.getDeclaredField("UPLOAD_DIR");
        uploadDirField.setAccessible(true);
        uploadDirField.set(null, tempDir.toAbsolutePath().toString() + "/");
        Path oldFilePath = tempDir.resolve("old-image.jpg");
        Files.write(oldFilePath, "old file".getBytes());
        userService.saveProfileImage(user, mockFile);
        verify(userRepository).save(user);
        String newImage = user.getImageUrl();
        assertNotNull(newImage);
        assertTrue(Files.exists(tempDir.resolve(newImage)));
        assertFalse(Files.exists(oldFilePath));
    }



}