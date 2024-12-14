package com.example.service;

import com.example.entity.*;
import com.example.entity.Thread;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.HashSet;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadCommentRepository threadCommentRepository;

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private ThreadCategoryRepository threadCategoryRepository;

    @Override
    public void run(String... args) {
        seedData();
    }

    private void seedData() {
        // Declare users outside of the if block for broader scope
        User regularUser;
        User modUser;
        User adminUser;

        // Seed Users, check if they already exist to avoid duplication
        if (userRepository.count() == 0) {
            regularUser = new User("gebruiker", "test@test.nl", "$2a$10$wboelo8zLJjg9ZGbqcavm.v5BbCAf6sYpRN5.WyR3txhFo5UnMa7a", Role.USER);
            modUser = new User("moderator", "moderator@test.nl", "$2a$10$GxsRveGw0cW3UXtQdRmf1.Qzn/rkzcUGoq6g13tW2vVeWNZHuZ5ii", Role.MODERATOR);
            adminUser = new User("beheerder", "beheerder@test.nl", "$2a$10$VUQXHMqh7s9uWuQUoMk.LONFk0IZO5CltVL8r8G0s5JIARBtzQOH6", Role.ADMIN);
            // Password van User: Test123
            // Password van Moderator: Mod123
            // Password van Admin: Admin123

            // Save all users in one batch operation
            userRepository.saveAll(Arrays.asList(regularUser, modUser, adminUser));
        } else {
            // Retrieve existing users from the database
            regularUser = userRepository.findByUsername("gebruiker").orElseThrow();
            modUser = userRepository.findByUsername("moderator").orElseThrow();
            adminUser = userRepository.findByUsername("beheerder").orElseThrow();
        }

// Seed Threads if none exist
        if (threadRepository.count() == 0) {
            // Check if categories already exist, if not create them
            ThreadCategory politicsCategory = threadCategoryRepository.findByName("Politiek").orElseGet(() -> {
                ThreadCategory category = new ThreadCategory("Politiek");
                return threadCategoryRepository.save(category);
            });

            ThreadCategory climateCategory = threadCategoryRepository.findByName("Klimaat").orElseGet(() -> {
                ThreadCategory category = new ThreadCategory("Klimaat");
                return threadCategoryRepository.save(category);
            });

            ThreadCategory economyCategory = threadCategoryRepository.findByName("Economie").orElseGet(() -> {
                ThreadCategory category = new ThreadCategory("Economie");
                return threadCategoryRepository.save(category);
            });

            ThreadCategory educationCategory = threadCategoryRepository.findByName("Onderwijs").orElseGet(() -> {
                ThreadCategory category = new ThreadCategory("Onderwijs");
                return threadCategoryRepository.save(category);
            });

            // Create Threads
            Thread thread1 = new Thread("Discussie over Verkiezingen 2024", "Wat is jouw mening over de uitslag?", "2024-11-20", regularUser);
            thread1.setCategories(new HashSet<>(Arrays.asList(politicsCategory)));

            Thread thread2 = new Thread("Klimaatbeleid en Toekomst", "Hoe belangrijk is klimaatverandering voor jou?", "2024-11-18", modUser);
            thread2.setCategories(new HashSet<>(Arrays.asList(climateCategory)));

            Thread thread3 = new Thread("Economie en Belastingen", "Wat vind je van de huidige belastingtarieven?", "2024-11-17", adminUser);
            thread3.setCategories(new HashSet<>(Arrays.asList(economyCategory)));

            Thread thread4 = new Thread("Onderwijs in Nederland", "Discussieer over de uitdagingen in het onderwijs", "2024-11-19", regularUser);
            thread4.setCategories(new HashSet<>(Arrays.asList(educationCategory)));

            // Save Threads
            threadRepository.saveAll(Arrays.asList(thread1, thread2, thread3, thread4));
        } else {
            System.out.println("Threads bestaan al, overslaan.");
        }



        // Seed Thread Comments if none exist
        if (threadCommentRepository.count() == 0) {
            Thread thread1 = threadRepository.findByTitle("Discussie over Verkiezingen 2024").orElseThrow();
            Thread thread2 = threadRepository.findByTitle("Klimaatbeleid en Toekomst").orElseThrow();
            Thread thread3 = threadRepository.findByTitle("Economie en Belastingen").orElseThrow();

            ThreadComment comment1 = new ThreadComment(regularUser, thread1, "Ik vond de verkiezingen eerlijk verlopen.", "2024-11-20");
            ThreadComment comment2 = new ThreadComment(modUser, thread2, "Klimaatverandering moet de hoogste prioriteit krijgen!", "2024-11-18");
            ThreadComment comment3 = new ThreadComment(adminUser, thread3, "Belastingverlaging is noodzakelijk voor de middenklasse.", "2024-11-17");
            ThreadComment comment4 = new ThreadComment(regularUser, thread1, "Ik denk dat er betere alternatieven waren.", "2024-11-21");

            threadCommentRepository.saveAll(Arrays.asList(comment1, comment2, comment3, comment4));
        } else {
            System.out.println("Thread Reacties bestaan al, overslaan.");
        }

        // Seed FAQs if none exist
        if (faqRepository.count() == 0) {
            Faq faq1 = new Faq("Wat is VerkiezingsDB?", "VerkiezingsDB is een platform voor real-time verkiezingsupdates en discussies.");
            Faq faq2 = new Faq("Hoe kan ik deelnemen?", "Registreer je en doe mee aan discussies over verschillende verkiezingsthema's.");
            Faq faq3 = new Faq("Is VerkiezingsDB gratis?", "Ja, VerkiezingsDB is gratis voor alle gebruikers.");
            Faq faq4 = new Faq("Hoe rapporteer ik een gebruiker?", "Klik op het profiel van de gebruiker en selecteer 'Rapporteer Gebruiker'.");
            Faq faq5 = new Faq("Hoe kan ik moderator worden?", "Neem contact op met de beheerder om te solliciteren als moderator.");
            Faq faq6 = new Faq("Hoe neem ik contact op met de support?", "Stuur een e-mail naar support@verkiezingsdb.nl voor hulp.");

            faqRepository.saveAll(Arrays.asList(faq1, faq2, faq3, faq4, faq5, faq6));
        } else {
            System.out.println("FAQs bestaan al, overslaan.");
        }

        // Seed Parties
        if (partyRepository.count() == 0) {
            Party party1 = new Party("GROENLINKS / Partij van de Arbeid (PvdA)",
                    "De Partij van de Arbeid (PvdA) streeft naar een rechtvaardige samenleving waarin solidariteit, gelijkheid, en sociale rechtvaardigheid centraal staan. " +
                            "De partij heeft een lange geschiedenis in de Nederlandse politiek en zet zich in voor het versterken van de publieke sector, het verbeteren van de arbeidsomstandigheden, " +
                            "en het bieden van gelijke kansen voor iedereen. PvdA pleit voor een progressief belastingbeleid waarbij de rijken meer bijdragen, en zet zich in voor meer investeringen in " +
                            "de zorg en het onderwijs, zodat iedereen kan profiteren van de welvaart. De partij wil ook actief werken aan het verduurzamen van de samenleving en het versterken van de " +
                            "arbeidsmarkt.");

            Party party2 = new Party("PVV (Partij voor de Vrijheid)",
                    "De PVV (Partij voor de Vrijheid) is een nationalistische en populistische partij die zich richt op het beschermen van de Nederlandse cultuur en identiteit. " +
                            "De partij pleit voor strengere immigratiebeperkingen, het versterken van de nationale soevereiniteit, en het herzien van het Europese beleid. PVV is tegen de invloed van " +
                            "de EU op nationale aangelegenheden en wil de controle over immigratie en grensbeveiliging terug naar Nederland. De partij benadrukt het belang van veiligheid en het " +
                            "beschermen van Nederlandse waarden en normen, terwijl het pleit voor minder overheidsbemoeienis in de samenleving.");

            Party party3 = new Party("D66",
                    "D66 is een sociaal-liberale partij die zich inzet voor een open en eerlijke samenleving. Ze zetten zich in voor de bescherming van de democratie, burgerrechten en het milieu. " +
                            "D66 pleit voor onderwijs voor iedereen, inclusief duurzame energieoplossingen, en het versterken van de Europese samenwerking op het gebied van klimaatverandering, migratie, " +
                            "en economische samenwerking. De partij wil meer macht geven aan de burger door middel van directe democratie, en pleit voor een inclusief beleid waarbij gelijke kansen voor " +
                            "iedereen voorop staan. D66 is daarnaast voorstander van meer transparantie in de politiek en pleit voor een duurzamere en socialere samenleving.");

            Party party4 = new Party("PVV",
                    "De Partij voor de Vrijheid (PVV) is een populistische en nationalistische partij die zich richt op het versterken van de Nederlandse identiteit en het beschermen van " +
                            "nationale soevereiniteit. De partij pleit voor strengere wetgeving rondom immigratie en terrorismebestrijding, en is kritisch over de invloed van de Europese Unie op de nationale politiek. " +
                            "De PVV is tegen de instroom van migranten uit niet-westerse landen en pleit voor een strikter asielbeleid. De partij heeft ook standpunten over het versterken van de veiligheid, " +
                            "het behouden van de traditionele Nederlandse waarden, en het verminderen van overheidsbemoeienis in het dagelijks leven. Daarnaast richt de partij zich op de verbetering van " +
                            "de Nederlandse economie door het verlagen van belastingen voor de middenklasse.");

            Party party5 = new Party("DENK",
                    "DENK is een politieke partij die zich richt op de belangen van etnische minderheden in Nederland en streeft naar gelijke behandeling voor iedereen. De partij wil racisme, " +
                            "discriminatie, en ongelijkheid bestrijden door middel van concrete beleidsmaatregelen en door het versterken van de rechten van migranten en minderheden. DENK wil meer aandacht voor " +
                            "diversiteit in de politiek, onderwijs, en de arbeidsmarkt. Ze pleiten ook voor sociale rechtvaardigheid, inclusief het verbeteren van de toegang tot zorg en het versterken van de sociale " +
                            "vangnetten. DENK wil dat alle burgers gelijke rechten hebben, ongeacht hun afkomst, en pleit voor een inclusieve samenleving waarin iedereen zich gehoord voelt.");

            Party party6 = new Party("Nieuw Sociaal Contract",
                    "Nieuw Sociaal Contract is een partij die zich richt op het versterken van de sociale zekerheid en het bevorderen van een duurzame samenleving. De partij pleit voor een herziening " +
                            "van het sociaal beleid, zodat het meer rekening houdt met de diversiteit van de samenleving. Ze willen investeringen in werkgelegenheid en de publieke sector, en streven naar een " +
                            "inclusieve samenleving waarin iedereen gelijke kansen heeft. De partij zet zich in voor het verbeteren van de werk- en leefsituatie van alle burgers, ongeacht achtergrond of " +
                            "economische status. Nieuw Sociaal Contract pleit voor beleid dat de kloof tussen arm en rijk verkleint, en een betere toegang biedt tot gezondheidszorg en onderwijs.");

            Party party7 = new Party("Partij voor de Dieren",
                    "De Partij voor de Dieren is de enige politieke partij in Nederland die zich specifiek richt op dierenwelzijn en het milieu. Ze pleiten voor strengere wetgeving tegen de " +
                            "bio-industrie, het beëindigen van de exploitatie van dieren, en het bevorderen van duurzame landbouwpraktijken. De partij heeft als doel de biodiversiteit te beschermen, klimaatverandering " +
                            "te bestrijden, en de natuur te behouden voor toekomstige generaties. Ze willen Nederland transformeren naar een samenleving waarin dierenrechten net zo belangrijk zijn als mensenrechten. " +
                            "Daarnaast pleit de partij voor de verduurzaming van de samenleving door het bevorderen van groene energie en het beschermen van de biodiversiteit.");

            Party party8 = new Party("Volt",
                    "Volt is een progressieve, pan-Europese partij die zich richt op het versterken van de samenwerking tussen Europese landen en het aanpakken van de grootste uitdagingen van deze tijd, " +
                            "zoals klimaatverandering, economische ongelijkheid en technologische veranderingen. Volt pleit voor een Europees sociaal contract, waarin alle lidstaten bijdragen aan een gezamenlijk beleid " +
                            "voor duurzaamheid, sociale rechtvaardigheid, en vrede. De partij wil een eerlijke verdeling van de welvaart, een circulaire economie en meer transparantie in Europese besluitvorming. Volt " +
                            "heeft als doel een verenigd Europa te creëren waarin samenwerking centraal staat, zodat gemeenschappelijke problemen gezamenlijk aangepakt kunnen worden.");

            Party party9 = new Party("SP",
                    "De Socialistische Partij (SP) is een partij die zich inzet voor het bevorderen van sociale gelijkheid en het bestrijden van armoede en ongelijkheid. De partij wil het kapitalistische systeem " +
                            "hervormen door het bevorderen van een solidaire samenleving waarin de belangen van de arbeider centraal staan. Ze pleiten voor het nationaliseren van belangrijke sectoren zoals de zorg en het " +
                            "onderwijs, en voor meer investeringen in sociale woningbouw en duurzame energie. De SP wil het belastingklimaat veranderen door rijke bedrijven en particulieren eerlijker bij te laten dragen " +
                            "aan de samenleving. Ze pleiten ook voor meer aandacht voor sociale rechtvaardigheid, zoals de bescherming van de rechten van arbeiders en een eerlijke verdeling van rijkdom.");

            Party party10 = new Party("Bij1",
                    "Bij1 is een progressieve, antiracistische partij die zich richt op het bestrijden van systematisch racisme en sociale ongelijkheid. De partij wil een samenleving waarin diversiteit en inclusie " +
                            "centraal staan, en waarin iedereen gelijke kansen heeft. Bij1 pleit voor een radicale herstructurering van het sociale vangnet, en wil meer focus op gelijke rechten voor alle burgers, ongeacht " +
                            "afkomst of seksuele geaardheid. Ze willen de economie vergroenen en meer aandacht voor de rechten van vrouwen, kinderen, en minderheden.");

            Party party11 = new Party("Forum voor Democratie",
                    "Forum voor Democratie (FvD) is een conservatieve en nationalistische partij die zich richt op het versterken van de nationale soevereiniteit en het afwijzen van Europese bemoeienis. " +
                            "De partij pleit voor een streng immigratiebeleid, het beschermen van de Nederlandse identiteit en cultuur, en het behoud van de nationale cultuur en tradities. FvD is kritisch over de " +
                            "mainstream media en wil de macht van de politiek en de elite terugdringen door middel van directe democratie.");

            Party party12 = new Party("BBB",
                    "BoerBurgerBeweging (BBB) is een partij die zich richt op de belangen van boeren en plattelandsbewoners. Ze pleiten voor duurzame landbouwpraktijken, het behoud van het platteland en het " +
                            "verbeteren van de situatie voor de agrarische sector. BBB wil ervoor zorgen dat boeren een eerlijke prijs krijgen voor hun producten en dat ze minder last hebben van overheidsmaatregelen die " +
                            "hen in hun werk belemmeren. De partij is voorstander van een onafhankelijk landbouwbeleid dat Nederland beschermt tegen de invloed van de EU.");

            Party party13 = new Party("ChristenUnie",
                    "De ChristenUnie is een christelijke politieke partij die zich richt op het bevorderen van de normen en waarden van het christendom in de samenleving. De partij pleit voor het versterken van " +
                            "de positie van gezinnen, het bevorderen van zorg voor ouderen en kinderen, en het ontwikkelen van beleid dat het welzijn van de samenleving centraal stelt. Ze willen een samenleving waarin " +
                            "recht, vrede, en gerechtigheid prevaleren, en waarin mensen in solidariteit met elkaar leven.");

            Party party14 = new Party("CDA",
                    "Het CDA is een centrumrechtse partij die zich richt op het bevorderen van samenwerking binnen de samenleving, waarbij economische groei, duurzaamheid en zorg voor kwetsbare groepen centraal staan. " +
                            "De partij pleit voor een sterkere rol voor de overheid in het waarborgen van sociale zekerheid en het versterken van de zorgsector. CDA is voor een evenwichtige benadering van economische " +
                            "groei, waarbij ook het milieu en de maatschappij niet uit het oog worden verloren.");

            Party party15 = new Party("JA21",
                    "JA21 is een politieke partij die zich richt op de belangen van de middenklasse, ondernemers, en de samenleving in het algemeen. Ze pleiten voor een lage belastingdruk, minder bureaucratie, en het " +
                            "versterken van de nationale identiteit. De partij is voorstander van minder overheidsbemoeienis en meer vrijheid voor bedrijven en burgers om hun eigen keuzes te maken, maar tegelijkertijd ook " +
                            "voor strengere wetgeving op het gebied van veiligheid en immigratie.");

            Party party16 = new Party("BVNL / Groep Van Haga",
                    "BVNL (Belang van Nederland) is een conservatief-liberale partij die zich richt op het versterken van de nationale soevereiniteit, het beperken van immigratie en het herstellen van de rechtsstaat. " +
                            "De partij wil de macht van de EU inperken en de nationale belangen weer vooropstellen. BVNL pleit voor belastingverlagingen, het beperken van overheidsuitgaven, en het versterken van de veiligheid " +
                            "in Nederland.");

            Party party17 = new Party("50PLUS",
                    "50PLUS is een politieke partij die zich richt op de belangen van ouderen, met specifieke aandacht voor pensioenen, gezondheidszorg en betaalbare woningbouw voor senioren. De partij pleit voor een " +
                            "herziening van het pensioenstelsel en voor meer steun voor ouderen in de zorg. 50PLUS wil ook dat ouderen een grotere stem krijgen in politieke besluitvorming en dat hun rechten beter beschermd worden.");

            Party party18 = new Party("Piratenpartij",
                    "De Piratenpartij is een politieke partij die zich richt op het beschermen van de privacy en burgerrechten, met name in het digitale tijdperk. De partij pleit voor meer transparantie van de " +
                            "overheid, het waarborgen van gegevensbescherming en de strijd tegen onterecht toezicht en censuur. De Piratenpartij is ook voor het hervormen van auteursrechten en het bevorderen van digitale " +
                            "vrijheid.");

            Party party19 = new Party("De Groenen",
                    "De Groenen zijn een ecologische partij die zich richt op de bescherming van het milieu, het bevorderen van duurzame economieën, en het beschermen van de natuur. Ze pleiten voor beleid dat gericht " +
                            "is op het behoud van biodiversiteit, de strijd tegen klimaatverandering en het versterken van het sociale vangnet. De partij is voor een circulaire economie, waarin grondstoffen zoveel mogelijk " +
                            "hergebruikt worden.");

            Party party20 = new Party("SGP",
                    "De Staatkundig Gereformeerde Partij (SGP) is een christelijke politieke partij die zich richt op het behoud van traditionele waarden en normen, met nadruk op Bijbelse principes. Ze pleiten voor " +
                            "een samenleving die wordt geleid door christelijke ethiek, waarin het gezin en het huwelijk centraal staan. De partij wil strengere wetgeving voor morele kwesties en pleit voor minder " +
                            "overheidsbemoeienis in het dagelijks leven.");

            Party party21 = new Party("Splinter",
                    "Splinter is een politieke partij die zich richt op het herstellen van het vertrouwen in de politiek en het versterken van de democratie. De partij pleit voor meer transparantie, verantwoording " +
                            "en burgerparticipatie in het politieke proces. Ze willen het huidige politieke systeem vernieuwen, zodat het weer dicht bij de burgers komt te staan.");

            Party party22 = new Party("LEF - Voor de Nieuwe Generatie",
                    "LEF is een partij die zich richt op het verbeteren van de kansen voor jongeren in Nederland. Ze pleiten voor meer aandacht voor jeugdwerkgelegenheid, innovatie in het onderwijs, en sociale " +
                            "mobiliteit. De partij wil jongeren de kans geven om hun talenten te ontwikkelen en te profiteren van nieuwe technologieën.");

            Party party23 = new Party("Nederland met een Plan",
                    "Nederland met een Plan is een partij die zich richt op het ontwikkelen van concrete, realistische plannen voor de toekomst van Nederland. Ze willen Nederland sterker maken door een focus op " +
                            "onderwijs, werkgelegenheid, duurzaamheid en sociale rechtvaardigheid. De partij wil beleid ontwikkelen dat alle burgers ten goede komt.");

            Party party24 = new Party("LP",
                    "De Libertaire Partij is een politieke partij die zich richt op het bevorderen van individuele vrijheid en het beperken van de macht van de overheid. Ze pleiten voor een minimalistische overheid, " +
                            "waarin de taken van de overheid zich beperken tot defensie, rechtspraak en politie. De partij wil meer economische vrijheid, lagere belastingen, en minder bureaucratie.");

            Party party25 = new Party("PartijvdSport",
                    "De Partij van de Sport richt zich op het verbeteren van de toegang tot en het stimuleren van sport en fysieke activiteit in Nederland. Ze pleiten voor investeringen in sportinfrastructuur, " +
                            "het toegankelijk maken van sport voor iedereen, en het verbeteren van de nationale gezondheid door middel van actieve leefstijlen.");

            Party party26 = new Party("Politieke Partij voor Basisinkomen",
                    "De Politieke Partij voor Basisinkomen pleit voor het invoeren van een onvoorwaardelijk basisinkomen voor alle burgers. De partij is van mening dat dit systeem de armoede kan verminderen, de " +
                            "sociale zekerheid kan verbeteren, en de vrijheid van mensen kan vergroten. Ze willen het huidige sociale vangnet hervormen en pleiten voor meer nadruk op gelijkheid.");

            Party party27 = new Party("Samen voor Nederland",
                    "Samen voor Nederland is een partij die zich richt op het bevorderen van sociale samenwerking, het versterken van de economie, en het verbeteren van de levenskwaliteit voor iedereen. De partij pleit " +
                            "voor meer nadruk op werkgelegenheid, onderwijs, en zorg, en wil de kloof tussen arm en rijk verkleinen.");

            Party party28 = new Party("VVD",
                    "De Volkspartij voor Vrijheid en Democratie (VVD) is een liberale politieke partij die zich richt op individuele vrijheid, verantwoordelijkheid en het versterken van de Nederlandse economie. " +
                            "De partij pleit voor belastingverlagingen, minder bureaucratie, en een efficiënter overheidsbeleid. VVD wil ook een sterke en stabiele internationale positie voor Nederland, met een focus op " +
                            "veiligheid, rechtsstaat, en Europese samenwerking.");

            // Save all parties
            partyRepository.saveAll(Arrays.asList(party1, party2, party3, party4, party5, party6, party7, party8, party9, party10, party11, party12, party13, party14, party15, party16, party17, party18, party19, party20, party21, party22, party23, party24, party25, party26, party27, party28));
        } else {
            System.out.println("Partijen bestaan al, overslaan.");
        }





        System.out.println("Gebruikers, Threads, Reacties en FAQs succesvol gezaaid als ze niet al bestonden!");
    }
}
