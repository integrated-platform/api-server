package controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class TestController {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /hello should return 'Hello, World!'")
    void sayHello_ShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())            // 200 OK 상태 확인
                .andExpect(content().string("Hello, World!"));  // 응답 내용 검증
    }
}
