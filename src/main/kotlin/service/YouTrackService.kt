package service

import model.Issue
import model.Notification
import model.Project

interface YouTrackService {
    /**
     * This method returns all notifications
     * @return List of [Notification] objects representing notifications
     */
    fun getAllNotifications() : List<Notification>

    /**
     * This method returns all projects
     * @return List of [Project] objects representing projects
     */
    fun getAllProjects() :List<Project>

    /**
     * This method creates a new issue
     * @param issue Issue to be created
     * @return [Boolean] indicating whether the issue was successfully created
     */
    fun createIssue(issue: Issue):Boolean
}