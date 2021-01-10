package am.itspace.projectscope.model;

public enum Type {
    MEMBER("TEAM_MEMBER"),
    LEADER("TEAM_LEADER");
    String type;

    Type(String t) {
        this.type = t;
    }
}
