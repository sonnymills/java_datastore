package com.odde;

import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataStoreBackendTest {

    @Test
    public void it_can_instantiate_a_data_backend_with_in_memory_backend(){

        DataStoreBackend dsb = new DataStoreBackend();
    }
    @Test
    public void it_throws_an_error_if_the_flatfile_backend_cant_access_the_file_system()  {
        assertThrows( FileNotFoundException.class, () -> {
            DataStoreBackend dataStoreBackend = new DataStoreBackend("no_file_test.yml");
        });

    }

}
