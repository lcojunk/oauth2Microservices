package leo.demo.demosearch.dummydatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import leo.demo.MicroserviceConfig;
import leo.demo.demosearch.model.*;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class ReferenceDatabase {

    public static Random random = new Random();

    private static String randomNumericString(int n) {
        return MicroserviceConfig.randomNumericString(n);
    }
    private static List<Reference> allReference = new ArrayList<>();
    private static List<Branch> allBranches = new ArrayList<>();
    private static List<Lob> allLobs = new ArrayList<>();
    private static List<Tech> allTech = new ArrayList<>();
    private static String[] clientNames = {
        "Volkswagen AG", "Daimler AG", "E.ON SE", "BMW AG", "Schwarz Beteiligungs GmbH",
        "BASF SE", "Siemens AG", "Metro AG", "Deutsche Telekom AG", "Lidl Stiftung & Co. KG",
        "Deutsche Post DHL Group", "Audi AG", "Rewe Group", "Robert Bosch GmbH", "RWE AG"
    };
    private static int[] branchPos = {
        0, 0, 1, 0, 2,
        2, 4, 2, 4, 2,
        3, 0, 2, 2, 2
    };
    private static String[] branchNames = {
        "Automotive", "Energiewirtschaft", "Handel",
        "Öffentliche Verwaltung", "Telekommunikation"

    };
    private static String[] clientDescription = {
        "Die Volkswagen Aktiengesellschaft (abgekürzt VW AG) mit Sitz in Wolfsburg ist der größte europäische Automobilhersteller und mit Toyota und General Motors einer der größten weltweit.[3][4] Die VW AG agiert als Muttergesellschaft der Fahrzeugmarken Volkswagen Pkw, Audi, Seat und Škoda sowie der Premiummarken Bentley, Bugatti, Ducati (Motorräder), Lamborghini und Porsche.[5] 2007 bis 2011 erweiterte der Konzern auch seine Nutzfahrzeugsparte (Lkw und Busse) mit der Marke Volkswagen Nutzfahrzeuge um die Unternehmen MAN und Scania.",
        "Die Daimler AG mit Sitz in Stuttgart ist ein börsennotierter Hersteller von Personenkraftwagen und Nutzfahrzeugen. Ihre bekannteste Marke ist Mercedes-Benz. Das Unternehmen ist außerdem Anbieter von Finanzdienstleistungen.",
        "Die börsennotierte E.ON SE (von englisch eon ‚Äon‘)[3] mit Hauptsitz in Düsseldorf ist der größte deutsche Energiekonzern und hauptsächlich im europäischen Gas- und Elektrizitätsgeschäft tätig.\n"
        + "\n"
        + "Der Energiekonzern gab am 27. April 2015 den Umzug des Firmensitzes mit den Kernsparten erneuerbare Energie, Netze und Kundendienstleistungen nach Essen bekannt. Außerdem wurde die Spaltung des Unternehmens mit der neu zu gründenden Gesellschaft Uniper für die Sparte Stromerzeugung (mit den Kern- und Kohlekraftwerken) und Energiehandel sowie Exploration mit Sitz in Düsseldorf mitgeteilt. E.ON behält dabei 40.000 Mitarbeiter und die neue Uniper 15.000 Mitarbeiter. Vorstandschef von Uniper wird der bisherige E.ON-Finanzvorstand Klaus Schäfer.[4]",
        "Die Bayerische Motoren Werke Aktiengesellschaft (BMW AG) ist die Muttergesellschaft der BMW Group, einem weltweit operierenden Automobil- und Motorradhersteller mit Sitz in München. Die Produktpalette umfasst die Automobil- und Motorrad-Marke BMW, die Automarken Mini und Rolls-Royce sowie die BMW-Submarken BMW M und BMW i.",
        "Die Schwarz Beteiligungs GmbH, der die Unternehmen Lidl und Kaufland gehören, ist der größte Handelskonzern Europas.\n"
        + "\n"
        + "Die Schwarz-Gruppe erzielte im Geschäftsjahr 2014/2015 einen Umsatz von 79,3 Milliarden Euro und lag damit im Umsatz vor dem Rivalen Metro. Eigentümer der Schwarz Beteiligungs GmbH sind die Dieter Schwarz Stiftung gGmbH (99,9 % der Anteile) und die Schwarz Unternehmenstreuhand KG (0,1 % der Anteile); letztere hält 100 % der Stimmrechte.[3] Der Sitz der Schwarz-Gruppe ist Neckarsulm.[4] Die Schwarz-Gruppe hatte im Jahr 2010 weltweit über 10.000 Filialen (rund 9000 Lidl-Filialen, davon 3200 in Deutschland, und rund 1000 Kaufland-Filialen, davon 580 in Deutschland).[5] Die Gruppe hat nach eigenen Angaben Filialen in 20 Staaten. Der Online-Handel wird von der Lidl E-Commerce International (ehemals Schwarz E-Commerce), einer Schwestergesellschaft von Lidl, betrieben.[6] Im Ranking der 500 größten Familienunternehmen der Zeitschrift Wirtschaftsblatt nimmt die Schwarz-Gruppe den ersten Platz ein.[7]",
        "Die BASF SE (ehemals „Badische Anilin- & Soda-Fabrik“) ist der nach Umsatz und Marktkapitalisierung weltweit größte Chemiekonzern. Weltweit sind etwa 113.000 Mitarbeiter in mehr als 80 Ländern bei der BASF beschäftigt. Die BASF betreibt über 390 Produktionsstandorte weltweit, ihr Hauptsitz befindet sich in Ludwigshafen am Rhein. 2014 erzielte das Unternehmen bei einem Umsatz von 74,3 Mrd. EUR ein EBIT von 7,4 Mrd. EUR. Die Aktie des Unternehmens ist im DAX an der Frankfurter Wertpapierbörse gelistet und wird ebenfalls an den Börsen in London und Zürich gehandelt.",
        "Die Siemens Aktiengesellschaft ist ein integrierter Technologiekonzern mit den vier Hauptgeschäftsfeldern Energie, Medizintechnik, Industrie sowie Infrastruktur und Städte. Als Telegraphen Bau-Anstalt von Siemens & Halske 1847 in Berlin von Werner Siemens (ab 1888: von Siemens) und Johann Georg Halske gegründet, ist der heutige Siemens-Konzern in 190 Ländern vertreten und zählt weltweit zu den größten Unternehmen der Elektrotechnik und Elektronik. Die Aktiengesellschaft mit Doppelsitz in Berlin und München unterhält 125 Standorte in Deutschland und ist im DAX an der Frankfurter Wertpapierbörse notiert.",
        "Die Metro Group, früher Metro AG, ist die Dachgesellschaft mehrerer Großhandels- und Einzelhandelsunternehmen. Die Gruppe mit Sitz in Düsseldorf beschäftigt knapp 300.000 Mitarbeiter, davon 110.000 in Deutschland (Stand: Dezember 2011[3]). Mit ihren Vertriebsmarken ist sie bisher in 33 Ländern aktiv.",
        "Die Deutsche Telekom AG ist eines der größten europäischen Telekommunikationsunternehmen.[2] Ihr Sitz ist in Bonn.\n"
        + "\n"
        + "Das Unternehmen betreibt technische Netze (ISDN, xDSL, Satelliten, Gigabit-Ethernet, ATM, 2G, 3G, 4G usw.) für den Betrieb von Informations- und Kommunikationsdiensten (IuK), etwa Telefonen (Festnetz und Mobilfunk) oder Onlinediensten.",
        "Lidl ist der größte Discounter in Europa und nach Aldi der zweitgrößte weltweit. Lidl betreibt in 26 europäischen Ländern ca. 9.900 Filialen.[4] Lidl ist Teil der Schwarz-Gruppe mit Sitz in Neckarsulm.",
        "Die Deutsche Post AG mit Sitz in Bonn ist das größte Logistik- und Postunternehmen der Welt. Unter dem Namen Deutsche Post DHL Group tritt der Konzern seit dem 11. März 2015 in der Öffentlichkeit auf. Das Unternehmen entstand 1995 durch Privatisierung der früheren Behörde Deutsche Bundespost und ist seit 2000 Bestandteil des deutschen Leitindexes DAX an der Frankfurter Wertpapierbörse. Mit Wirkung vom 23. September 2013 zog die Deutsche Post in den EURO STOXX 50 ein.[3]",
        "Die Audi AG (Eigenschreibweise: AUDI AG) mit Sitz in Ingolstadt in Bayern ist ein deutscher Automobilhersteller, der dem Volkswagen-Konzern angehört.\n"
        + "\n"
        + "Der Markenname ist ein Wortspiel zur Umgehung der Namensrechte des ehemaligen Kraftfahrzeugherstellers A. Horch & Cie. Motorwagenwerke Zwickau. Unternehmensgründer August Horch, der „seine“ Firma nach Zerwürfnissen mit dem Finanzvorstand verlassen hatte, suchte einen Namen für sein neues Unternehmen und fand ihn im Vorschlag eines Zwickauer Gymnasiasten, der Horch ins Lateinische übersetzte.[2] Audi ist der Imperativ Singular von audire (zu Deutsch hören, zuhören) und bedeutet „Höre!“ oder eben „Horch!“. Am 25. April 1910 wurde die Audi Automobilwerke GmbH Zwickau in das Handelsregister der Stadt Zwickau eingetragen.",
        "Die Rewe Group, eigene Schreibweise REWE Group, ist ein deutscher Handels- und Touristikkonzern mit Sitz in Köln. Das Akronym steht für „Revisionsverband der Westkauf-Genossenschaften“. Unternehmensschwerpunkte bilden der Lebensmittelhandel und die Touristiksparte. Die bedeutendsten Unternehmen unter dem Dach der Rewe Group sind die REWE-Zentral-AG sowie die REWE-Zentralfinanz eG in Köln.",
        "Die Robert Bosch GmbH ist ein im Jahr 1886 von Robert Bosch gegründetes deutsches Unternehmen. Es ist tätig als Automobilzulieferer, Hersteller von Gebrauchsgütern (Elektrowerkzeuge, Haushaltsgeräte) und Industrie- und Gebäudetechnik (Sicherheitstechnik) und darüber hinaus in der automatisierten Verpackungstechnik, wo Bosch den ersten Platz einnimmt.",
        "Die RWE AG (bis 1990 Rheinisch-Westfälisches Elektrizitätswerk AG) mit Sitz in Essen ist ein börsennotierter Energieversorgungskonzern. Am Umsatz gemessen ist er der zweitgrößte Versorger Deutschlands. Der Konzern gehört auch in den Niederlanden seit der Übernahme von Essent zu den führenden Energieversorgern und ist auch in anderen Märkten (z. B. Großbritannien, Belgien, Österreich, Osteuropa, Türkei) vertreten."
    };

    private static void addReference(String name, String description) {
        Reference reference = new Reference(name, description);
        reference.setId(randomNumericString(15));
        allReference.add(reference);
    }

    public static List<Reference> getAllReference() {
        addReference("Vanille dea Oerba", "Final Fantasy XXIII");
        addReference("Fang dea Oerba", "Final Fantasy XXIII");
        addReference("Andersen", "Mass Effect 1-3");
        return allReference;
    }

    public static void setAllReference(List<Reference> allReference) {
        ReferenceDatabase.allReference = allReference;
    }

    public static List<Reference> createReferences(int n) {

        List<Branch> branches = new ArrayList<>();
        for (int i = 0; i < branchNames.length; i++) {
            branches.add(new Branch(randomNumericString(15), branchNames[i], "Branche Nr." + i));
        }
        List<Lob> lobs = new ArrayList<>();
        lobs.add(new Lob(randomNumericString(15), "Business Consulting", "Line Of Business 1"));
        lobs.add(new Lob(randomNumericString(15), "IT-Consulting", "Line Of Business 2"));
        lobs.add(new Lob(randomNumericString(15), "Software Development", "Line Of Business 3"));
        lobs.add(new Lob(randomNumericString(15), "IT-Management", "Line Of Business 4"));
        List<Tech> technics = new ArrayList<>();
        technics.add(new Tech(randomNumericString(15), "C++", "Programmiersprache"));
        technics.add(new Tech(randomNumericString(15), "Windows 2008 Server", "Server Betriebssystem"));
        technics.add(new Tech(randomNumericString(15), "Debian Linux", "Linux Betriebssystem"));
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        Reference reference;
        List<Reference> references = new ArrayList<>();
        long projectStartMs, projectEndMs, todayMs, oneDay = 24 * 60 * 60 * 1000;
        int randN;
        for (int i = 0; i < n; i++) {
            reference = new Reference(randomNumericString(15),
                    lobs.get(i % lobs.size()).getName() + " Projekt für " + clientNames[i % clientNames.length],
                    loremIpsum);
            reference.setClientname(clientNames[i % clientNames.length]);
            reference.setClientdescription(clientDescription[i % clientDescription.length]);
            reference.setBranch(branches.get(branchPos[i % branchNames.length]));
            reference.setLob(lobs.get(i % lobs.size()));
            reference.setTech(technics.get(i % technics.size()));
            reference.setPersondays(i * 10);
            randN = random.nextInt(i * 10000 + 1) + 10000;
            reference.setVolume(randN / 100.0);
            todayMs = new Date().getTime();
            projectStartMs = todayMs - random.nextInt(12) * 30 * oneDay;
            projectEndMs = projectStartMs + (random.nextInt(24) + 1) * 30 * oneDay;
            reference.setProjectstart(new Date(projectStartMs));
            reference.setProjectend(new Date(projectEndMs));
            references.add(reference);
        }

        return references;
    }
}
