package com.example.service;

import com.example.entity.*;
import com.example.entity.Thread;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public void run(String... args) {
        seedUsers();
        seedCategories();
        seedThreads();
        seedThreadComments();
        seedFaqs();
        seedParties();
        seedLikes();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            List<User> users = Arrays.asList(
                    new User("gebruiker", "test@test.nl", "$2a$10$wboelo8zLJjg9ZGbqcavm.v5BbCAf6sYpRN5.WyR3txhFo5UnMa7a", User.Role.USER),
                    new User("moderator", "moderator@test.nl", "$2a$10$GxsRveGw0cW3UXtQdRmf1.Qzn/rkzcUGoq6g13tW2vVeWNZHuZ5ii", User.Role.MODERATOR),
                    new User("beheerder", "beheerder@test.nl", "$2a$10$VUQXHMqh7s9uWuQUoMk.LONFk0IZO5CltVL8r8G0s5JIARBtzQOH6", User.Role.ADMIN)
                    // Password van User: Test123
                    // Password van Moderator: Mod123
                    // Password van Admin: Admin123
            );
            userRepository.saveAll(users);
        }
    }

    private void seedCategories() {
        List<String> categoryNames = Arrays.asList("Politiek", "Klimaat", "Economie", "Onderwijs");
        categoryNames.forEach(name -> threadCategoryRepository.findByName(name).orElseGet(() -> threadCategoryRepository.save(new ThreadCategory(name))));
    }

    private void seedThreads() {
        if (threadRepository.count() == 0) {
            User regularUser = userRepository.findByUsername("gebruiker").orElseThrow();
            User modUser = userRepository.findByUsername("moderator").orElseThrow();
            User adminUser = userRepository.findByUsername("beheerder").orElseThrow();

            ThreadCategory politicsCategory = threadCategoryRepository.findByName("Politiek").orElseThrow();
            ThreadCategory climateCategory = threadCategoryRepository.findByName("Klimaat").orElseThrow();
            ThreadCategory economyCategory = threadCategoryRepository.findByName("Economie").orElseThrow();
            ThreadCategory educationCategory = threadCategoryRepository.findByName("Onderwijs").orElseThrow();

            List<Thread> threads = Arrays.asList(
                    new Thread("Discussie over Verkiezingen 2024", "Wat is jouw mening over de uitslag?", "2024-11-20", regularUser, new HashSet<>(List.of(politicsCategory))),
                    new Thread("Klimaatbeleid en Toekomst", "Hoe belangrijk is klimaatverandering voor jou?", "2024-11-18", modUser, new HashSet<>(List.of(climateCategory))),
                    new Thread("Economie en Belastingen", "Wat vind je van de huidige belastingtarieven?", "2024-11-17", adminUser, new HashSet<>(List.of(economyCategory))),
                    new Thread("Onderwijs in Nederland", "Discussieer over de uitdagingen in het onderwijs", "2024-11-19", regularUser, new HashSet<>(List.of(educationCategory)))
            );
            threadRepository.saveAll(threads);
        }
    }

    private void seedThreadComments() {
        if (threadCommentRepository.count() == 0) {
            User regularUser = userRepository.findByUsername("gebruiker").orElseThrow();
            User modUser = userRepository.findByUsername("moderator").orElseThrow();
            User adminUser = userRepository.findByUsername("beheerder").orElseThrow();

            Thread thread1 = threadRepository.findByTitle("Discussie over Verkiezingen 2024").orElseThrow();
            Thread thread2 = threadRepository.findByTitle("Klimaatbeleid en Toekomst").orElseThrow();
            Thread thread3 = threadRepository.findByTitle("Economie en Belastingen").orElseThrow();

            List<ThreadComment> comments = Arrays.asList(
                    new ThreadComment(regularUser, thread1, "Ik vond de verkiezingen eerlijk verlopen.", "2024-11-20"),
                    new ThreadComment(modUser, thread2, "Klimaatverandering moet de hoogste prioriteit krijgen!", "2024-11-18"),
                    new ThreadComment(adminUser, thread3, "Belastingverlaging is noodzakelijk voor de middenklasse.", "2024-11-17"),
                    new ThreadComment(regularUser, thread1, "Ik denk dat er betere alternatieven waren.", "2024-11-21")
            );
            threadCommentRepository.saveAll(comments);
        }
    }

    private void seedFaqs() {
        if (faqRepository.count() == 0) {
            List<Faq> faqs = Arrays.asList(
                    new Faq("Wat is VerkiezingsDB?", "VerkiezingsDB is een platform voor real-time verkiezingsupdates en discussies."),
                    new Faq("Hoe kan ik deelnemen?", "Registreer je en doe mee aan discussies over verschillende verkiezingsthema's."),
                    new Faq("Is VerkiezingsDB gratis?", "Ja, VerkiezingsDB is gratis voor alle gebruikers."),
                    new Faq("Hoe rapporteer ik een gebruiker?", "Klik op het profiel van de gebruiker en selecteer 'Rapporteer Gebruiker'."),
                    new Faq("Hoe kan ik moderator worden?", "Neem contact op met de beheerder om te solliciteren als moderator."),
                    new Faq("Hoe neem ik contact op met de support?", "Stuur een e-mail naar support@verkiezingsdb.nl voor hulp.")
            );
            faqRepository.saveAll(faqs);
        }
    }

    private void seedParties() {
        if (partyRepository.count() == 0) {
            List<Party> parties = Arrays.asList(
                    new Party("GROENLINKS / Partij van de Arbeid (PvdA)", "De Partij van de Arbeid (PvdA) streeft naar een rechtvaardige samenleving waarin solidariteit, gelijkheid, en sociale rechtvaardigheid centraal staan. \" +\n" +
                            "De partij heeft een lange geschiedenis in de Nederlandse politiek en zet zich in voor het versterken van de publieke sector, het verbeteren van de arbeidsomstandigheden," +
                            "en het bieden van gelijke kansen voor iedereen. PvdA pleit voor een progressief belastingbeleid waarbij de rijken meer bijdragen, en zet zich in voor meer investeringen in" +
                            "de zorg en het onderwijs, zodat iedereen kan profiteren van de welvaart. De partij wil ook actief werken aan het verduurzamen van de samenleving en het versterken van de" +
                            "arbeidsmarkt."),
                    new Party("PVV (Partij voor de Vrijheid)", "De PVV (Partij voor de Vrijheid) is een nationalistische en populistische partij die zich richt op het beschermen van de Nederlandse cultuur en identiteit. \" +\n" +
                            "De partij pleit voor strengere immigratiebeperkingen, het versterken van de nationale soevereiniteit, en het herzien van het Europese beleid. PVV is tegen de invloed van" +
                            "de EU op nationale aangelegenheden en wil de controle over immigratie en grensbeveiliging terug naar Nederland. De partij benadrukt het belang van veiligheid en het" +
                            "beschermen van Nederlandse waarden en normen, terwijl het pleit voor minder overheidsbemoeienis in de samenleving."),
                    new Party("D66", "D66 is een sociaal-liberale partij die zich inzet voor een open en eerlijke samenleving. Ze zetten zich in voor de bescherming van de democratie, burgerrechten en het milieu. \" +\n" +
                            "D66 pleit voor onderwijs voor iedereen, inclusief duurzame energieoplossingen, en het versterken van de Europese samenwerking op het gebied van klimaatverandering, migratie," +
                            "en economische samenwerking. De partij wil meer macht geven aan de burger door middel van directe democratie, en pleit voor een inclusief beleid waarbij gelijke kansen voor" +
                            "iedereen voorop staan. D66 is daarnaast voorstander van meer transparantie in de politiek en pleit voor een duurzamere en socialere samenleving."),
                    new Party("PVV",
                            "De Partij voor de Vrijheid (PVV) is een populistische en nationalistische partij die zich richt op het versterken van de Nederlandse identiteit en het beschermen van " +
                            "nationale soevereiniteit. De partij pleit voor strengere wetgeving rondom immigratie en terrorismebestrijding, en is kritisch over de invloed van de Europese Unie op de nationale politiek. " +
                            "De PVV is tegen de instroom van migranten uit niet-westerse landen en pleit voor een strikter asielbeleid. De partij heeft ook standpunten over het versterken van " +
                            "de veiligheid, het behouden van de traditionele Nederlandse waarden, en het verminderen van overheidsbemoeienis in het dagelijks leven. Daarnaast richt de partij zich op de verbetering van " +
                            "de Nederlandse economie door het verlagen van belastingen voor de middenklasse."),
                    new Party("DENK",
                            "DENK is een politieke partij die zich richt op de belangen van etnische minderheden in Nederland en streeft naar gelijke behandeling voor iedereen. De partij wil racisme, " +
                            "discriminatie, en ongelijkheid bestrijden door middel van concrete beleidsmaatregelen en door het versterken van de rechten van migranten en minderheden. DENK wil meer aandacht voor " +
                            "diversiteit in de politiek, onderwijs, en de arbeidsmarkt. Ze pleiten ook voor sociale rechtvaardigheid, inclusief het verbeteren van de toegang tot zorg en het versterken van de sociale " +
                            "vangnetten. DENK wil dat alle burgers gelijke rechten hebben, ongeacht hun afkomst, en pleit voor een inclusieve samenleving waarin iedereen zich gehoord voelt."),
                    new Party("Nieuw Sociaal Contract", "Nieuw Sociaal Contract is een partij die zich richt op het versterken van de sociale zekerheid en het bevorderen van een duurzame samenleving. De partij pleit voor een herziening " +
                            "van het sociaal beleid, zodat het meer rekening houdt met de diversiteit van de samenleving. Ze willen investeringen in werkgelegenheid en de publieke sector, en streven naar een " +
                            "inclusieve samenleving waarin iedereen gelijke kansen heeft. De partij zet zich in voor het verbeteren van de werk- en leefsituatie van alle burgers, ongeacht achtergrond of " +
                            "economische status. Nieuw Sociaal Contract pleit voor beleid dat de kloof tussen arm en rijk verkleint, en een betere toegang biedt tot gezondheidszorg en onderwijs."),
                    new Party("Partij voor de Dieren", "De Partij voor de Dieren is de enige politieke partij in Nederland die zich specifiek richt op dierenwelzijn en het milieu. Ze pleiten voor strengere wetgeving tegen de " +
                            "bio-industrie, het beëindigen van de exploitatie van dieren, en het bevorderen van duurzame landbouwpraktijken. De partij heeft als doel de biodiversiteit te beschermen, klimaatverandering " +
                            "te bestrijden, en de natuur te behouden voor toekomstige generaties. Ze willen Nederland transformeren naar een samenleving waarin dierenrechten net zo belangrijk zijn als mensenrechten. " +
                            "Daarnaast pleit de partij voor de verduurzaming van de samenleving door het bevorderen van groene energie en het beschermen van de biodiversiteit."),
                    new Party("Volt", "Volt is een progressieve, pan-Europese partij die zich richt op het versterken van de samenwerking tussen Europese landen en het aanpakken van de grootste uitdagingen van deze tijd, " +
                            "zoals klimaatverandering, economische ongelijkheid en technologische veranderingen. Volt pleit voor een Europees sociaal contract, waarin alle lidstaten bijdragen aan een gezamenlijk beleid " +
                            "voor duurzaamheid, sociale rechtvaardigheid, en vrede. De partij wil een eerlijke verdeling van de welvaart, een circulaire economie en meer transparantie in Europese besluitvorming. Volt " +
                            "heeft als doel een verenigd Europa te creëren waarin samenwerking centraal staat, zodat gemeenschappelijke problemen gezamenlijk aangepakt kunnen worden."),
                    new Party("SP", "De Socialistische Partij (SP) is een partij die zich inzet voor het bevorderen van sociale gelijkheid en het bestrijden van armoede en ongelijkheid. De partij wil het kapitalistische systeem " +
                            "hervormen door het bevorderen van een solidaire samenleving waarin de belangen van de arbeider centraal staan. Ze pleiten voor het nationaliseren van belangrijke sectoren zoals de zorg en het " +
                            "onderwijs, en voor meer investeringen in sociale woningbouw en duurzame energie. De SP wil het belastingklimaat veranderen door rijke bedrijven en particulieren eerlijker bij te laten dragen " +
                            "aan de samenleving. Ze pleiten ook voor meer aandacht voor sociale rechtvaardigheid, zoals de bescherming van de rechten van arbeiders en een eerlijke verdeling van rijkdom."),
                    new Party("BIJ1", "Bij1 is een progressieve, antiracistische partij die zich richt op het bestrijden van systematisch racisme en sociale ongelijkheid. De partij wil een samenleving waarin diversiteit en inclusie " +
                            "centraal staan, en waarin iedereen gelijke kansen heeft. Bij1 pleit voor een radicale herstructurering van het sociale vangnet, en wil meer focus op gelijke rechten voor alle burgers, ongeacht " +
                            "afkomst of seksuele geaardheid. Ze willen de economie vergroenen en meer aandacht voor de rechten van vrouwen, kinderen, en minderheden."),
                    new Party("Forum voor Democratie", "Forum voor Democratie (FvD) is een conservatieve en nationalistische partij die zich richt op het versterken van de nationale soevereiniteit en het afwijzen van Europese bemoeienis. " +
                            "De partij pleit voor een streng immigratiebeleid, het beschermen van de Nederlandse identiteit en cultuur, en het behoud van de nationale cultuur en tradities. FvD is kritisch over de " +
                            "mainstream media en wil de macht van de politiek en de elite terugdringen door middel van directe democratie."),
                    new Party("BBB", "BoerBurgerBeweging (BBB) is een partij die zich richt op de belangen van boeren en plattelandsbewoners. Ze pleiten voor duurzame landbouwpraktijken, het behoud van het platteland en het " +
                            "verbeteren van de situatie voor de agrarische sector. BBB wil ervoor zorgen dat boeren een eerlijke prijs krijgen voor hun producten en dat ze minder last hebben van overheidsmaatregelen die " +
                            "hen in hun werk belemmeren. De partij is voorstander van een onafhankelijk landbouwbeleid dat Nederland beschermt tegen de invloed van de EU."),
                    new Party("ChristenUnie", "De ChristenUnie is een christelijke politieke partij die zich richt op het bevorderen van de normen en waarden van het christendom in de samenleving. De partij pleit voor het versterken van " +
                            "de positie van gezinnen, het bevorderen van zorg voor ouderen en kinderen, en het ontwikkelen van beleid dat het welzijn van de samenleving centraal stelt. Ze willen een samenleving waarin " +
                            "recht, vrede, en gerechtigheid prevaleren, en waarin mensen in solidariteit met elkaar leven."),
                    new Party("CDA",  "Het CDA is een centrumrechtse partij die zich richt op het bevorderen van samenwerking binnen de samenleving, waarbij economische groei, duurzaamheid en zorg voor kwetsbare groepen centraal staan. " +
                            "De partij pleit voor een sterkere rol voor de overheid in het waarborgen van sociale zekerheid en het versterken van de zorgsector. CDA is voor een evenwichtige benadering van economische " +
                            "groei, waarbij ook het milieu en de maatschappij niet uit het oog worden verloren."),
                    new Party("JA21", "JA21 is een politieke partij die zich richt op de belangen van de middenklasse, ondernemers, en de samenleving in het algemeen. Ze pleiten voor een lage belastingdruk, minder bureaucratie, en het " +
                            "versterken van de nationale identiteit. De partij is voorstander van minder overheidsbemoeienis en meer vrijheid voor bedrijven en burgers om hun eigen keuzes te maken, maar tegelijkertijd ook " +
                            "voor strengere wetgeving op het gebied van veiligheid en immigratie."),
                    new Party("BVNL / Groep Van Haga", "BVNL (Belang van Nederland) is een conservatief-liberale partij die zich richt op het versterken van de nationale soevereiniteit, het beperken van immigratie en het herstellen van de rechtsstaat. " +
                            "De partij wil de macht van de EU inperken en de nationale belangen weer vooropstellen. BVNL pleit voor belastingverlagingen, het beperken van overheidsuitgaven, en het versterken van de veiligheid " +
                            "in Nederland."),
                    new Party("50PLUS", "50PLUS is een politieke partij die zich richt op de belangen van ouderen, met specifieke aandacht voor pensioenen, gezondheidszorg en betaalbare woningbouw voor senioren. De partij pleit voor een " +
                            "herziening van het pensioenstelsel en voor meer steun voor ouderen in de zorg. 50PLUS wil ook dat ouderen een grotere stem krijgen in politieke besluitvorming en dat hun rechten beter beschermd worden."),
                    new Party("Piratenpartij","De Piratenpartij is een politieke partij die zich richt op het beschermen van de privacy en burgerrechten, met name in het digitale tijdperk. De partij pleit voor meer transparantie van de " +
                            "overheid, het waarborgen van gegevensbescherming en de strijd tegen onterecht toezicht en censuur. De Piratenpartij is ook voor het hervormen van auteursrechten en het bevorderen van digitale " +
                            "vrijheid."),
                    new Party("De Groenen","De Groenen zijn een ecologische partij die zich richt op de bescherming van het milieu, het bevorderen van duurzame economieën, en het beschermen van de natuur. Ze pleiten voor beleid dat gericht " +
                            "is op het behoud van biodiversiteit, de strijd tegen klimaatverandering en het versterken van het sociale vangnet. De partij is voor een circulaire economie, waarin grondstoffen zoveel mogelijk " +
                            "hergebruikt worden."),
                    new Party("SGP","De Staatkundig Gereformeerde Partij (SGP) is een christelijke politieke partij die zich richt op het behoud van traditionele waarden en normen, met nadruk op Bijbelse principes. Ze pleiten voor " +
                            "een samenleving die wordt geleid door christelijke ethiek, waarin het gezin en het huwelijk centraal staan. De partij wil strengere wetgeving voor morele kwesties en pleit voor minder " +
                            "overheidsbemoeienis in het dagelijks leven."),
                    new Party("Splinter", "Splinter is een politieke partij die zich richt op het herstellen van het vertrouwen in de politiek en het versterken van de democratie. De partij pleit voor meer transparantie, verantwoording " +
                            "en burgerparticipatie in het politieke proces. Ze willen het huidige politieke systeem vernieuwen, zodat het weer dicht bij de burgers komt te staan."),
                    new Party("LEF - Voor de Nieuwe Generatie", "LEF is een partij die zich richt op het verbeteren van de kansen voor jongeren in Nederland. Ze pleiten voor meer aandacht voor jeugdwerkgelegenheid, innovatie in het onderwijs, en sociale " +
                            "mobiliteit. De partij wil jongeren de kans geven om hun talenten te ontwikkelen en te profiteren van nieuwe technologieën."),
                    new Party("Nederland met een Plan", "Nederland met een Plan is een partij die zich richt op het ontwikkelen van concrete, realistische plannen voor de toekomst van Nederland. Ze willen Nederland sterker maken door een focus op " +
                            "onderwijs, werkgelegenheid, duurzaamheid en sociale rechtvaardigheid. De partij wil beleid ontwikkelen dat alle burgers ten goede komt."),
                    new Party("LP", "De Libertaire Partij is een politieke partij die zich richt op het bevorderen van individuele vrijheid en het beperken van de macht van de overheid. Ze pleiten voor een minimalistische overheid, " +
                            "waarin de taken van de overheid zich beperken tot defensie, rechtspraak en politie. De partij wil meer economische vrijheid, lagere belastingen, en minder bureaucratie."),
                    new Party("PartijvdSport", "De Partij van de Sport richt zich op het verbeteren van de toegang tot en het stimuleren van sport en fysieke activiteit in Nederland. Ze pleiten voor investeringen in sportinfrastructuur, " +
                            "het toegankelijk maken van sport voor iedereen, en het verbeteren van de nationale gezondheid door middel van actieve leefstijlen."),
                    new Party("Politieke Partij voor Basisinkomen", "De Politieke Partij voor Basisinkomen pleit voor het invoeren van een onvoorwaardelijk basisinkomen voor alle burgers. De partij is van mening dat dit systeem de armoede kan verminderen, de " +
                            "sociale zekerheid kan verbeteren, en de vrijheid van mensen kan vergroten. Ze willen het huidige sociale vangnet hervormen en pleiten voor meer nadruk op gelijkheid."),
                    new Party("Samen voor Nederland", "Samen voor Nederland is een partij die zich richt op het bevorderen van sociale samenwerking, het versterken van de economie, en het verbeteren van de levenskwaliteit voor iedereen. De partij pleit " +
                            "voor meer nadruk op werkgelegenheid, onderwijs, en zorg, en wil de kloof tussen arm en rijk verkleinen."),
                    new Party("VVD", "De Volkspartij voor Vrijheid en Democratie (VVD) is een liberale politieke partij die zich richt op individuele vrijheid, verantwoordelijkheid en het versterken van de Nederlandse economie. " +
                            "De partij pleit voor belastingverlagingen, minder bureaucratie, en een efficiënter overheidsbeleid. VVD wil ook een sterke en stabiele internationale positie voor Nederland, met een focus op " +
                            "veiligheid, rechtsstaat, en Europese samenwerking.")
            );
            partyRepository.saveAll(parties);
        }
    }

    private void seedLikes() {
        if (likeRepository.count() == 0) {
            User regularUser = userRepository.findByUsername("gebruiker").orElseThrow();
            User modUser = userRepository.findByUsername("moderator").orElseThrow();
            User adminUser = userRepository.findByUsername("beheerder").orElseThrow();

            Thread thread1 = threadRepository.findByTitle("Discussie over Verkiezingen 2024").orElseThrow();
            Thread thread2 = threadRepository.findByTitle("Klimaatbeleid en Toekomst").orElseThrow();
            Thread thread3 = threadRepository.findByTitle("Economie en Belastingen").orElseThrow();

            List<Like> likes = Arrays.asList(
                    new Like(regularUser, Like.VoteType.UPVOTE, thread1),
                    new Like(modUser, Like.VoteType.DOWNVOTE, thread1),
                    new Like(adminUser, Like.VoteType.UPVOTE, thread2),
                    new Like(regularUser, Like.VoteType.DOWNVOTE, thread2),
                    new Like(modUser, Like.VoteType.UPVOTE, thread3),
                    new Like(adminUser, Like.VoteType.UPVOTE, thread3)
            );
            likeRepository.saveAll(likes);
        }
    }
}
