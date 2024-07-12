package com.aluracursos.DesafioLiteratura.model;

public enum Languages {
    ES("es"),
    EN("en"),
    FR("fr"),
    PT("pt");

    private String language;
    Languages(String language){
        this.language = language;
    }
    public static Languages fromString(String text) {
        for (Languages languages : Languages.values()) {
            if (languages.language.equalsIgnoreCase(text)) {
                return languages;
            }
        }
        System.out.println("Ningun lengauje encontrado: " + text);
        return null;
    }
}
