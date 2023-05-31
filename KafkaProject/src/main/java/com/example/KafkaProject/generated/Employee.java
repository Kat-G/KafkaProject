package com.example.KafkaProject.generated;

import jakarta.xml.bind.annotation.*;


/**
 * <p>Java class for Employee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Employee"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="post" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Employee", propOrder = {
    "name",
    "post",
    "email",
    "age"
})
@XmlRootElement(name = "Employee")
public class Employee
{
    @XmlElement(required = true)
    protected String name;
    protected String post;
    protected String email;
    protected int age;

    /**
     * Gets the value of the name property.
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the post property.
     *
     */
    public String getPost() {
        return post;
    }

    /**
     * Sets the value of the post property.
     *
     */
    public void setPost(String value) {
        this.post = value;
    }

    /**
     * Gets the value of the email property.
     *
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the age property.
     *
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     *
     */
    public void setAge(int value) {
        this.age = value;
    }
}
