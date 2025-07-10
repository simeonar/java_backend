package com.sagedemo.backend.common.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {
    @Test
    void ok_setsSuccessAndData() {
        ApiResponse<String> resp = ApiResponse.ok("test");
        assertTrue(resp.isSuccess());
        assertEquals("OK", resp.getMessage());
        assertEquals("test", resp.getData());
        assertNull(resp.getError());
    }

    @Test
    void error_setsErrorAndMessage() {
        ApiResponse<String> resp = ApiResponse.error("Not found", "NOT_FOUND");
        assertFalse(resp.isSuccess());
        assertEquals("Not found", resp.getMessage());
        assertNull(resp.getData());
        assertEquals("NOT_FOUND", resp.getError());
    }

    @Test
    void settersAndGetters_work() {
        ApiResponse<Integer> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setMessage("msg");
        resp.setData(123);
        resp.setError("ERR");
        assertTrue(resp.isSuccess());
        assertEquals("msg", resp.getMessage());
        assertEquals(123, resp.getData());
        assertEquals("ERR", resp.getError());
    }
}
