package com.github.liachmodded.jiragrabber;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import java.net.URI;

public final class Main {

  static final String MC_VERSION = "19w35a";

  public static void main(String... args) throws Exception {
    JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
    JiraRestClient client = factory.create(URI.create("https://bugs.mojang.com/"), new AnonymousAuthenticationHandler());

    SearchResult result = client.getSearchClient().searchJql("project %3D MC AND fixVersion %3D " + MC_VERSION + " ORDER BY created ASC").claim();

    for (Issue issue : result.getIssues()) {
      System.out.println(issue.getKey() + " – " + issue.getSummary());
      System.out.println(issue.getKey() + " – ");
    }

    System.out.println("{{fixes|fixedin=" + MC_VERSION + "|prefix=");
    System.out.println("|;old");
    for (Issue issue : result.getIssues()) {
      System.out.println("|" + issue.getKey().substring(3) + "|" + issue.getSummary());
    }
    System.out.println("}}");
  }

}
