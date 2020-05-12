package com.odde;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class DataStoreBackendTest {

    @Test
    public void it_can_instantiate_a_data_backend_with_in_memory_backend(){

        DataStoreBackend dsb = new DataStoreBackend();
    }
    @Test
    public void it_throws_an_error_if_the_flatfile_backend_cant_access_the_file_system(){
        assertException( FilesystemNotAccessible, DataStoreBackend dsb = new DataStoreBackend("test.yml");

    }

}
