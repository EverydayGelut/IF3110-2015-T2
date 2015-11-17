/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.stackx;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.me.stackx.model.AnswerModel;
import org.me.stackx.model.QuestionModel;
import org.me.stackx.model.UserModel;
import org.me.stackx.module.Answer;
import org.me.stackx.module.Question;
import org.me.stackx.module.User;

/**
 *
 * @author natanelia
 */
@WebService(serviceName = "ws")
public class ws {
    /**
     * Web service operation
     * @return createdQuestionId
     */
    @WebMethod(operationName = "createQuestion")
    public int createQuestion(@WebParam(name = "access_token") String access_token, @WebParam(name = "title") String title, @WebParam(name = "content") String content) {
        return QuestionModel.create(access_token, title, content);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllQuestions")
    public Question[] getAllQuestions() {
        return QuestionModel.getAll();
    }

    /**
     * Web service operation
     * @param id
     * @return question
     */
    @WebMethod(operationName = "getQuestionById")
    public Question getQuestionById(@WebParam(name = "id") final int id) {
        return QuestionModel.getById(id);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editQuestion")
    public int editQuestion(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id, @WebParam(name = "title") final String title, @WebParam(name = "content") final String content) {
        return QuestionModel.edit(access_token, id, title, content);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteUp")
    public int voteUpQuestion(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id) {
        return QuestionModel.vote(access_token, id, 1);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteDownQuestion")
    public int voteDownQuestion(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id) {
        return QuestionModel.vote(access_token, id, -1);
    }
    
    
    /**
     * Web service operation
     * @return createdAnswerId
     */
    @WebMethod(operationName = "createAnswer")
    public int createAnswer(@WebParam(name = "access_token") String access_token, @WebParam(name = "question_id") int question_id, @WebParam(name = "content") String content) {
        return AnswerModel.create(access_token, question_id, content);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAllAnswersFromQuestionId")
    public Answer[] getAllAnswersFromQuestionId(@WebParam(name = "id") final int id) {
        return AnswerModel.getAllFromQuestionId(id);
    }

    /**
     * Web service operation
     * @param id
     * @return question
     */
    @WebMethod(operationName = "getAnswerById")
    public Answer getAnswerById(@WebParam(name = "id") final int id) {
        return AnswerModel.getById(id);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "editAnswer")
    public int editAnswer(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id, @WebParam(name = "content") final String content) {
        return AnswerModel.edit(access_token, id, content);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteUpAnswer")
    public int voteUpAnswer(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id) {
        return AnswerModel.vote(access_token, id, 1);
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteDownAnswer")
    public int voteDownAnswer(@WebParam(name = "access_token") String access_token, @WebParam(name = "id") final int id) {
        return AnswerModel.vote(access_token, id, -1);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public String registerUser(@WebParam(name = "name") final String name, @WebParam(name = "email") final String email, @WebParam(name = "password") final String password) {
        return UserModel.register(name, email, password);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getUserById")
    public User getUserById(@WebParam(name = "id") final int id) {
        return UserModel.getById(id);
    }
    
    
}