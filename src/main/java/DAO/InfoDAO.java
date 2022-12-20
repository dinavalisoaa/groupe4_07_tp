/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package DAO;

/**
 *
 * @author dina
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author dina
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InfoDAO {
    public String[]ignore()default {""};
    public String table() default "";
    public boolean validable() default false;
}
