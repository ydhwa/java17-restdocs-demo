package com.example.java17restdocsdemo.controller;

import com.example.java17restdocsdemo.base.BaseTestController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class MemberControllerTest extends BaseTestController {

    private final String basePath = "/api/members";


    @Test
    void getMembers() throws Exception {
        getAction(basePath)
                .andExpect(status().isOk())
                .andDo(document("{method-name}",
                        getPagingRequestParametersSnippet(
                                parameterWithName("name").description("name (allow partial match)").attributes(typeAttribute(JsonFieldType.STRING)).optional()
                        ),
                        getPagingLinksSnippet(),
                        getPagingResponseFieldsSnippet(
                                fieldWithPath("_embedded.members").description("list of member").type(JsonFieldType.ARRAY),
                                fieldWithPath("_embedded.members[].id").description("id").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.members[].name").description("name").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.members[].age").description("age").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.members[].insertedAt").description("inserted timestamp").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.members[].updatedAt").description("updated timestamp").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    void getMember() throws Exception {
        getAction(basePath + "/{id}", 1)
                .andExpect(status().isOk())
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id of member").attributes(typeAttribute(JsonFieldType.NUMBER)).optional()
                        ),
                        getLinksSnippet(),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.member").description("member").type(JsonFieldType.OBJECT),
                                fieldWithPath("_embedded.member.id").description("id").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.name").description("name").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.age").description("age").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.insertedAt").description("inserted timestamp").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.updatedAt").description("updated timestamp").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    void insertMember() throws Exception {
        JSONObject param = new JSONObject()
                .put("name", "허준")
                .put("age", 50);

        postAction(param, basePath)
                .andExpect(status().isCreated())
                .andDo(document("{method-name}",
                        requestFields(
                                fieldWithPath("name").description("name").type(JsonFieldType.STRING).attributes(maxLengthAttribute(100)),
                                fieldWithPath("age").description("age").type(JsonFieldType.NUMBER)
                        ),
                        getLinksSnippet(),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.member").description("member").type(JsonFieldType.OBJECT),
                                fieldWithPath("_embedded.member.id").description("id").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.name").description("name").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.age").description("age").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.insertedAt").description("inserted timestamp").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.updatedAt").description("updated timestamp").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    void updateMember() throws Exception {
        JSONObject param = new JSONObject()
                .put("name", "허준")
                .put("age", 50);

        putAction(param, basePath + "/{id}", 1)
                .andExpect(status().isOk())
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id of member").attributes(typeAttribute(JsonFieldType.NUMBER)).optional()
                        ),
                        requestFields(
                                fieldWithPath("name").description("name").type(JsonFieldType.STRING).attributes(maxLengthAttribute(100)),
                                fieldWithPath("age").description("age").type(JsonFieldType.NUMBER)
                        ),
                        getLinksSnippet(),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.member").description("member").type(JsonFieldType.OBJECT),
                                fieldWithPath("_embedded.member.id").description("id").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.name").description("name").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.age").description("age").type(JsonFieldType.NUMBER),
                                fieldWithPath("_embedded.member.insertedAt").description("inserted timestamp").type(JsonFieldType.STRING),
                                fieldWithPath("_embedded.member.updatedAt").description("updated timestamp").type(JsonFieldType.STRING).optional()
                        )
                ));
    }

    @Test
    void deleteMember() throws Exception {
        deleteAction(basePath + "/{id}", 1)
                .andExpect(status().isOk())
                .andDo(document("{method-name}",
                        pathParameters(
                                parameterWithName("id").description("id of member").attributes(typeAttribute(JsonFieldType.NUMBER)).optional()
                        ),
                        getLinksSnippet(),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.member").description("member").type(JsonFieldType.OBJECT),
                                fieldWithPath("_embedded.member.id").description("id").type(JsonFieldType.NUMBER)
                        )
                ));
    }
}
