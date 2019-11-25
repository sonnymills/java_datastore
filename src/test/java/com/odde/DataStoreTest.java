package com.odde;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStoreTest {


    private DataStore ds;

    @BeforeEach
    public void setUp() throws Exception {
        ds = DataStore.getDataStoreInstance(true);
    }

    @Test
    public void repository_starts_without_objects(){
        assertEquals(0, ds.getKeys().size());
    }

    @Test
    public void datastore_can_store_a_simple_string(){
        ds.addObject("thisString","wow");
        assertEquals("wow",ds.getObject("thisString"));
    }
    @Test
    public void datastore_instances_return_same_thing(){
        DataStore dsAdd = DataStore.getDataStoreInstance();
        dsAdd.addObject("thisString","wow");
        DataStore dsGet = DataStore.getDataStoreInstance();
        assertEquals("wow",dsGet.getObject("thisString"));
    }
    @Test
    public void complex_object_survives_across_instances(){
        DataStore dsAdd = DataStore.getDataStoreInstance();
        ComplexThing thing = new ComplexThing();
        dsAdd.addObject("complexThing",thing);
        thing.setName("bob");
        DataStore dsGet = DataStore.getDataStoreInstance();
        ComplexThing thingUpdated = (ComplexThing) dsGet.getObject("complexThing");
        assertEquals("bob", thingUpdated.getName());


    }
    @Test
    public void no_key_does_something_good(){
       ComplexThing item = (ComplexThing) ds.getObject("notHere");
       assertEquals(null,item);
    }
    @Test
    public void try_to_reproduce_comodification(){
        ds.addObject("thing1", new ComplexThing("thing1"));
        ds.addObject("thing2", new ComplexThing("thing2"));
        ds.addObject("thing3", new ComplexThing("thing3"));
        ds.addObject("thing3", new ComplexThing("thing4"));
        ds.addObject("thing3", new ComplexThing("thing5"));
        List<Object> thingList = ds.getObjectList();
        for (Object o : thingList) {
            ComplexThing thing = (ComplexThing) o;
            thing.updateCounter(4);
        }

        for (Object o : thingList) {
            ComplexThing thing = (ComplexThing) o;
            thing.updateOtherCounter(2);
        }
        ComplexThing thing = (ComplexThing) thingList.get(0);
        assertEquals(4, thing.counter);
        assertEquals(2,thing.otherCounter);
    }
    @Test
    public void data_store_can_reset_the_internal_dataStore_and_start_with_no_items(){
        ds.addObject("thing1", new ComplexThing("thing1"));
        ds.addObject("thing2", new ComplexThing("thing2"));
        ds.addObject("thing3", new ComplexThing("thing3"));
        ds.addObject("thing4", new ComplexThing("thing4"));
        ds.addObject("thing5", new ComplexThing("thing5"));
        List<Object> thingList = ds.getObjectList();
        for (Object o : thingList) {
            ComplexThing thing = (ComplexThing) o;
            thing.updateCounter(4);
        }

        for (Object o : thingList) {
            ComplexThing thing = (ComplexThing) o;
            thing.updateOtherCounter(2);
        }
        ComplexThing thing = (ComplexThing) thingList.get(0);
        assertEquals(4, thing.counter);
        assertEquals(2,thing.otherCounter);
        DataStore dsRead = DataStore.getDataStoreInstance(false);
        ComplexThing thing5 = (ComplexThing) dsRead.getObject("thing5");
        assertEquals(2, thing5.otherCounter);
    }
    @Test
    public void the_application_can_be_set_to_a_future_date(){
        LocalDate today = LocalDate.now();
        assertEquals(today.toString(), ds.getToday());
        Long oneDay = 1L;
        LocalDate tomorrow = today.plusDays(oneDay);
        ds.setCurrentDate(tomorrow);
        assertEquals(tomorrow.toString(),ds.getToday());
    }

    @Test
    public void clearing_the_datastore_resets_date(){
        LocalDate today = LocalDate.now();
        assertEquals(today.toString(), ds.getToday());
        Long oneDay = 1L;
        LocalDate tomorrow = today.plusDays(oneDay);
        ds.setCurrentDate(tomorrow);
        DataStore.resetDataStore();
        assertEquals(today.toString(),ds.getToday());

    }
    private class ComplexThing{
        private int counter;
        private int otherCounter;

        public ComplexThing(){};
        public ComplexThing(String name){
            setName(name);
        }
        private String name;
        public List<String> data = new ArrayList<>();
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }

        public void updateCounter(int i) {
            counter = i;
        }

        public void updateOtherCounter(int i) {
            otherCounter = i;
        }
    }

}
