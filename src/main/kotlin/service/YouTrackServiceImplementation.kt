package service

import model.Issue
import model.Notification
import model.Project
import util.Utils

class YouTrackServiceImplementation(private val YOU_TRACK_URL:String, private val TOKEN: String, private val utils: Utils) : YouTrackService {

    override fun getAllNotifications() :List<Notification> {
        val notifications = utils.fetchData("$YOU_TRACK_URL/api/users/notifications?fields=id,content", TOKEN)
        val allNotifications = mutableListOf<Notification>()

        for(notification in notifications) {
            val id = notification.get("id").asText()
            val content = utils.decompressAndDecode(notification.get("content").asText())
            allNotifications.add(Notification(id, content))
        }

        return allNotifications
    }

    override fun getAllProjects() :List<Project> {
        val projects = utils.fetchData("$YOU_TRACK_URL/api/admin/projects?fields=id,name", TOKEN)
        val allProjects = mutableListOf<Project>()

        for (project in projects) {
            val id = project.get("id").asText()
            val name = project.get("name").asText()
            allProjects.add(Project(id, name))
        }

        return allProjects
    }

    override fun createIssue(issue: Issue) :Boolean {
        val responseCode = utils.postData("$YOU_TRACK_URL/api/issues", issue, TOKEN)

        return responseCode == 200
    }
}