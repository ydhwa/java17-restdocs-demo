package com.example.java17restdocsdemo.base;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.hypermedia.LinkDescriptor;
import org.springframework.restdocs.hypermedia.LinksSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@ActiveProfiles("local")
public class BaseTestController {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(
                        documentationConfiguration(restDocumentation)
                                .uris()
                                .withScheme("http")
                                .withHost("localhost")
                                .withPort(8000)
                                .and()
                                .operationPreprocessors()
                                .withRequestDefaults(prettyPrint())
                                .withResponseDefaults(prettyPrint())
                )
                .alwaysDo(print())
                .build();
    }

    // return ResultActions on request method
    protected ResultActions getAction(String urlTemplate, Object... urlVariables) throws Exception {
        return this.mockMvc.perform(get(urlTemplate, urlVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/hal+json;charset=UTF-8")
        );
    }
    protected ResultActions postAction(JSONObject param, String urlTemplate, Object... urlVariables) throws Exception {
        return this.mockMvc.perform(post(urlTemplate, urlVariables)
                .content(param.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/hal+json;charset=UTF-8")
        );
    }
    protected ResultActions putAction(JSONObject param, String urlTemplate, Object... urlVariables) throws Exception {
        return this.mockMvc.perform(put(urlTemplate, urlVariables)
                .content(param.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/hal+json;charset=UTF-8")
        );
    }
    protected ResultActions deleteAction(String urlTemplate, Object... urlVariables) throws Exception {
        return this.mockMvc.perform(delete(urlTemplate, urlVariables)
                .contentType(MediaType.APPLICATION_JSON)
                .accept("application/hal+json;charset=UTF-8")
        );
    }

    // reusing snippets
    protected final RequestParametersSnippet getPagingRequestParametersSnippet(ParameterDescriptor... parameterDescriptors) {
        List<ParameterDescriptor> useParameterDescriptors = new ArrayList<>(Arrays.asList(parameterDescriptors));
        useParameterDescriptors.addAll(this.pagingRequestParameterDescriptors);
        return requestParameters(useParameterDescriptors);
    }
    protected final LinksSnippet getLinksSnippet(LinkDescriptor... linkDescriptors) {
        List<LinkDescriptor> useLinkDescriptors = new ArrayList<>(Arrays.asList(linkDescriptors));
        useLinkDescriptors.addAll(this.linkDescriptors);
        return links(halLinks(), useLinkDescriptors);
    }
    protected final LinksSnippet getPagingLinksSnippet(LinkDescriptor... linkDescriptors) {
        List<LinkDescriptor> useLinkDescriptors = new ArrayList<>(Arrays.asList(linkDescriptors));
        useLinkDescriptors.addAll(this.linkDescriptors);
        useLinkDescriptors.addAll(this.pagingLinkDescriptors);
        return links(halLinks(), useLinkDescriptors);
    }
    protected final ResponseFieldsSnippet getPagingResponseFieldsSnippet(FieldDescriptor... fieldDescriptors) {
        List<FieldDescriptor> useFieldDescriptors = new ArrayList<>(Arrays.asList(fieldDescriptors));
        useFieldDescriptors.addAll(this.pagingResponseFieldDescriptors);
        return relaxedResponseFields(useFieldDescriptors);
    }

    // reusing descriptors
    private final List<ParameterDescriptor> pagingRequestParameterDescriptors = Arrays.asList(
            parameterWithName("page").description("The page to retrieve (default=1)").attributes(typeAttribute(JsonFieldType.NUMBER)).optional(),
            parameterWithName("size").description("Entries per page (default=20)").attributes(typeAttribute(JsonFieldType.NUMBER)).optional()
    );
    private final List<LinkDescriptor> linkDescriptors = Arrays.asList(
            linkWithRel("self").description("link to self"),
            linkWithRel("profile").description("document link")
    );
    private final List<LinkDescriptor> pagingLinkDescriptors = Arrays.asList(
            linkWithRel("first").description("The first page of results").optional(),
            linkWithRel("last").description("The last page of results").optional(),
            linkWithRel("next").description("The next page of results").optional(),
            linkWithRel("prev").description("The previous page of results").optional()
    );
    private final List<FieldDescriptor> pagingResponseFieldDescriptors = Arrays.asList(
            fieldWithPath("page.size").description("elements size per page(default=20)"),
            fieldWithPath("page.totalElements").description("total element count of results"),
            fieldWithPath("page.totalPages").description("total page count"),
            fieldWithPath("page.number").description("current page")
    );

    // other attributes
    protected Attributes.Attribute maxLengthAttribute(int maxLength) {
        return new Attributes.Attribute("maxLength", maxLength);
    }
    protected Attributes.Attribute typeAttribute(JsonFieldType fieldType) {
        return new Attributes.Attribute("type", fieldType.toString());
    }
}
