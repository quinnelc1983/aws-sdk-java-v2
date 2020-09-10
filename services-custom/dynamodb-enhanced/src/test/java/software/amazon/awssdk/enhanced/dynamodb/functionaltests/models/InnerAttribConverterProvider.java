package software.amazon.awssdk.enhanced.dynamodb.functionaltests.models;


import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;

/**
 * Event Payload Converter to save the record on the class
 */
public class InnerAttribConverterProvider<T> implements AttributeConverterProvider {


    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return new InnerAttribConverter<T>();
    }
}