package net.umask.akna.web.components;

import org.apache.wicket.model.Model;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author JoGeraerts
 * @since 16-sep-2010 - 22:13:20
 */
public class SanitizingStringModelTest {

    @Test
    public void testGetObject(){
        String unsanitized ="<p>Je collega van de verloskamer vraagt of Kira misschien besneden is, omdat zij pas een documentaire over besneden vrouwen heeft gezien. Daar werd gezegd dat zeker 90% van de Somalische vrouwen besneden is. Wat doe je met deze informatie?</p>\n" +
                "<table border=\"0\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>abc</td>\n" +
                "<td>def</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>dd</td>\n" +
                "<td>ddd</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>";
        SanitizingStringModel m = new SanitizingStringModel(new Model<String>(unsanitized));
        assertFalse(m.getObject().contains("<br/>"));
    }
}
