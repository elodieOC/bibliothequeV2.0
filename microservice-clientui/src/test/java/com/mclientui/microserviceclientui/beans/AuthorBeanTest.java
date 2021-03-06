package com.mclientui.microserviceclientui.beans;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AuthorBeanTest {


    private AuthorBean author;
    private BookBean book1;
    private BookBean book2;

    @Before
    public void setUp() throws Exception {
        /* jeu de données */
        author=new AuthorBean();
        author.setId(2);
        author.setFirstName("Newton");
        author.setLastName("Scamender");
        author.setBirthDate("13/12/1924");
        author.setNationality("Anglaise");

        book1 = new BookBean();
        book1.setTitle("Animaux Fantastiques");
        book2 = new BookBean();
        book2.setTitle("L'ennui le plus long");

        List<BookBean> bookList = new ArrayList<>();
        bookList.add(book1);

        author.setBooks(bookList);
    }

    @Test (expected = Test.None.class)
    public void getSetFirstName() {
        Assert.assertEquals("Newton", author.getFirstName());
        Assert.assertNotEquals("Jean-Jacques", author.getFirstName());
        author.setFirstName("Jean-Jacques");
        Assert.assertEquals("Jean-Jacques", author.getFirstName());
        Assert.assertNotEquals("Newton", author.getFirstName());
    }

    @Test (expected = Test.None.class)
    public void getLastName() {
        Assert.assertEquals("Scamender", author.getLastName());
        Assert.assertNotEquals("Levy", author.getLastName());
        author.setLastName("Levy");
        Assert.assertEquals("Levy", author.getLastName());
        Assert.assertNotEquals("Scamender", author.getLastName());
    }

    @Test (expected = Test.None.class)
    public void getNationality() {
        Assert.assertEquals("Anglaise", author.getNationality());
        Assert.assertNotEquals("Française", author.getNationality());
        author.setNationality("Française");
        Assert.assertEquals("Française", author.getNationality());
        Assert.assertNotEquals("Anglaise", author.getNationality());
    }

    @Test (expected = Test.None.class)
    public void getBirthDate() {
        Assert.assertEquals("13/12/1924", author.getBirthDate());
        Assert.assertNotEquals("13/06/1983", author.getBirthDate());
        author.setBirthDate("13/06/1983");
        Assert.assertEquals("13/06/1983", author.getBirthDate());
        Assert.assertNotEquals("13/12/1924", author.getBirthDate());
    }

    @Test (expected = Test.None.class)
    public void getBooks() {
        Assert.assertEquals("[BookBean{id=null, ref='null', title='Animaux Fantastiques'}]", author.getBooks().toString());
        Assert.assertNotEquals("[BookBean{id=null, ref='AX567RT', title='null'}]", author.getBooks().toString());
        author.getBooks().add(book2);
        Assert.assertEquals("[BookBean{id=null, ref='null', title='Animaux Fantastiques'}, BookBean{id=null, ref='null', title='L'ennui le plus long'}]", author.getBooks().toString());
        Assert.assertNotEquals("[BookBean{id=null, ref='null', title='Animaux Fantastiques'}]", author.getBooks().toString());
    }

    @Test (expected = Test.None.class)
    public void toStringT() {
        Assert.assertEquals("AuthorBean{id=2, firstName='Newton', lastName='Scamender', nationality='Anglaise', birthDate='13/12/1924', books=[BookBean{id=null, ref='null', title='Animaux Fantastiques'}]}", author.toString());
        Assert.assertNotEquals("AuthorBean{id=5, firstName='Jean-Jacques', lastName='Levy'}", author.toString());
    }
}