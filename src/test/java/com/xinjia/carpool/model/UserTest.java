package com.xinjia.carpool.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for User.
 */
public class UserTest {

  private User user;

  /**
   * Test getters and setters.
   */
  @Test
  public void testGetterAndSetter() {

    user = new User();

    // First set of fields
    Long id1 = 1L;
    String name1 = "Evan Chen";
    Gender gender1 = Gender.MALE;
    String note1 = "See you at the front door";
    String phone1 = "310-123-4567";
    String username1 = "evanlovescars";
    String password1 = "ferrari";

    // Second set of fields
    Long id2 = 2L;
    String name2 = "Ocelia Zhang";
    Gender gender2 = Gender.FEMALE;
    String note2 = "I prefer non-smoking cars.";
    String phone2 = "618-987-6543";
    String username2 = "ocelia";
    String password2 = "zhazhaorjiajia";

    // Test first set of fields
    user.setId(id1);
    user.setName(name1);
    user.setGender(gender1);
    user.setNote(note1);
    user.setPhone(phone1);
    user.setUsername(username1);
    user.setPassword(password1);

    assertEquals(id1, user.getId());
    assertEquals(name1, user.getName());
    assertEquals(gender1, user.getGender());
    assertEquals(note1, user.getNote());
    assertEquals(phone1, user.getPhone());
    assertEquals(username1, user.getUsername());
    assertEquals(password1, user.getPassword());

    // Test second set of fields
    user.setId(id2);
    user.setName(name2);
    user.setGender(gender2);
    user.setNote(note2);
    user.setPhone(phone2);
    user.setUsername(username2);
    user.setPassword(password2);

    assertEquals(id2, user.getId());
    assertEquals(name2, user.getName());
    assertEquals(gender2, user.getGender());
    assertEquals(note2, user.getNote());
    assertEquals(phone2, user.getPhone());
    assertEquals(username2, user.getUsername());
    assertEquals(password2, user.getPassword());
  }
}
