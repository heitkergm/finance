package com.dappermoose.finance.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


/**
 * The Class YesNoConverter.
 *
 * @author Matt Heitker
 */
@Converter (autoApply = true)
public class YesNoConverter implements AttributeConverter<YesNoEnum, String>
{
    /*
     * (non-Javadoc)
     *
     * @see
     * jakarta.persistence.AttributeConverter#convertToDatabaseColumn(java.lang
     * .Object)
     */
    @Override
    public String convertToDatabaseColumn (final YesNoEnum yesNo)
    {
        String retVal;
        switch (yesNo)
        {
            case YES:
                retVal = "YES";
                break;
            case NO:
                retVal = "NO";
                break;
            default:
                throw new IllegalArgumentException ("Unknown value: "
                        + yesNo.toString ());
        }
        return retVal;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * jakarta.persistence.AttributeConverter#convertToEntityAttribute(java.lang
     * .Object)
     */
    @Override
    // @SuppressWarnings ("PMD.UnnecessaryFullyQualifiedName")
    public YesNoEnum convertToEntityAttribute (final String dbData)
    {
        YesNoEnum retVal;
        switch (dbData)
        {
            case "YES":
                retVal = YesNoEnum.YES;
                break;
            case "NO":
                retVal = YesNoEnum.NO;
                break;
            default:
                throw new IllegalArgumentException ("Unknown Value: "
                        + dbData.toString ());
        }
        return retVal;
    }
}
