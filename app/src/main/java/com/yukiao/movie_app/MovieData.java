package com.yukiao.movie_app;

import android.content.Context;

import java.util.ArrayList;

public class MovieData {

    private static final String TITLE [] = {
            "Avengers: Age of Ultron",
            "Avengers: Endgame",
            "Clash of the Titans",
            "Fast & Furious: Hobbs & Shaw",
            "Percy Jackson & the Lightning Thief",
            "Mission: Impossible - Fallout",
            "Percy Jackson: Sea of Monsters",
            "Skiptrace",
            "The Fast and the Furious: Tokyo Drift",
            "Wrath of the Titans"
    };
    private static final String GENRE [] = {

            "Action, Adventure, Sci-Fi",
            "Action, Adventure, Drama, Sci-Fi",
            "Action, Adventure, Fantasy",
            "Action, Adventure, Thriller",
            "Adventure, Family, Fantasy",
            "Action, Adventure, Thriller",
            "Adventure, Family, Fantasy, Thriller",
            "Action, Adventure, Comedy, Crime, Thriller",
            "Action, Crime, Thriller",
            "Action, Adventure, Fantasy"
    };
    private static final String  DESCRIPTION[] = {
            "Tony Stark creates the Ultron Program to protect the world, but when the peacekeeping program becomes hostile, The Avengers go into action to try and defeat a virtually impossible enemy together. Earth's mightiest heroes must come together once again to protect the world from global extinction",
            "After the devastating events of Avengers: Infinity War (2018), the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos's actions and undo the chaos to the universe, no matter what consequences may be in store, and no matter who they face...",
            "Born of god but raised by humans, Perseus, the demigod son of mighty Zeus, the king of the gods, vows to take his revenge on Hades, the terrifying ruler of the Underworld, when he sees his mortal family perish. As the race of men summons up the courage to rebel against the gods of Olympus, the doomed city of Argos becomes a battlefield, as Hades threatens to unleash the legendary sea-monster, Kraken, unless the fair Princess Andromeda willingly offers herself as a sacrifice. Now, having nothing more to lose, Perseus embarks on a daring, peril-laden quest to stop the forces of evil before Hades plunges the world into chaos and darkness. But, to survive the relentless onslaught of demons and terrible adversaries, Perseus must first embrace his destiny. Will Perseus defy the gods, and save humankind in the clash of the Titans?",
            "Lawman Luke Hobbs (Dwayne \"The Rock\" Johnson) and outcast Deckard Shaw (Jason Statham) form an unlikely alliance when a cyber-genetically enhanced villain threatens the future of humanity.",
            "When his lightning bolt is stolen, Zeus accuses Poseidon's son Percy Jackson and gives Poseidon's son fourteen days to return it, otherwise he will initiate a war amongst the gods. Meanwhile the teenager, Percy, who is dyslexic and has ADHD is visiting The Museum of Metropolitan of Art and is attacked by a Fury disguised in his teacher. His physically handicapped best friend Grover reveals that Percy is a demigod and that he is his protector and his teacher Mr Brunner gives him a pen telling him that it is a powerful weapon. They go to Percy's house and together with his mother Sally they drive to the Camp Half-Blood. However Sally is attacked by a Minotaur and vanishes before Percy can help her. In the camp, Percy befriends the gorgeous Annabeth; when they are attacked by Hades who wants the lighting bolt for himself, Percy discovers that his mother is in the Underworld with Hades. Percy decides to travel on a dangerous quest to retrieve the lightning bolt and save his mother. ",
            "Two years after Ethan Hunt had successfully captured Solomon Lane, the remnants of the Syndicate have reformed into another organization called the Apostles. Under the leadership of a mysterious fundamentalist known only as John Lark, the organization is planning on acquiring three plutonium cores. Ethan and his team are sent to Berlin to intercept them, but the mission fails when Ethan saves Luther and the Apostles escape with the plutonium. With CIA agent August Walker joining the team, Ethan and his allies must now find the plutonium cores before it's too late",
            "In this retelling of Rick Riordans book, \"The Sea of Monsters\", Percy Jackson, accompanied by his friends Annabeth Chase, Clarisse La Rue and Tyson, his half brother, goes on a journey to the Sea of Monsters to retrieve the Golden Fleece and save Camp Half-Blood. ",
            "Still blaming himself for the untimely demise of his best friend after nine long years, the veteran Hong Kong police detective, Bennie Chan, still finds himself on a wild-goose chase, trying to expose the elusive criminal kingpin known only as \"The Matador\". So far, nothing has changed, and Bennie's main suspect--the corrupt entrepreneur, Victor Wong--is, above all, legally untouchable. However, when Samantha--his late partner's only daughter--has a brush with the mob, Bennie will have no other choice but to seek help from an improbable ally: the flamboyant American gambler, Conor Watts, who has serious problems of his own with the Russian mafia. Now, the unlikely duo must cross the vast landscapes of Mongolia and the Gobi Desert to return to Hong Kong in one piece, as the Matador is still unknown. Will Bennie ever find the hard evidence he needs to put him once and for all behind bars?",
            "An American teenager named Sean Boswell is a loner in school, however he challenges his rival for an illegal street racing, and he totals his car in the end of the race. To avoid time in prison he is sent to Tokyo to live with his father who is in the military. As soon as he arrives he discovers a new, fun but dangerous way of street racing in the underworld of the streets of Tokyo, Japan",
            "A decade after his heroic defeat of the monstrous Kraken, Perseus-the demigod son of Zeus-is attempting to live a quieter life as a village fisherman and the sole parent to his 10-year old son, Helius. Meanwhile, a struggle for supremacy rages between the gods and the Titans. Dangerously weakened by humanity's lack of devotion, the gods are losing control of the imprisoned Titans and their ferocious leader, Kronos, father of the long-ruling brothers Zeus, Hades and Poseidon. The triumvirate had overthrown their powerful father long ago, leaving him to rot in the gloomy abyss of Tartarus, a dungeon that lies deep within the cavernous underworld. Perseus cannot ignore his true calling when Hades, along with Zeus' godly son, Ares (Edgar Ramírez), switch loyalty and make a deal with Kronos to capture Zeus. The Titans' strength grows stronger as Zeus' remaining godly powers are siphoned, and hell is unleashed on earth. Enlisting the help of the warrior Queen Andromeda (Rosamund Pike)"
    };

    private  static final int MOVIE_COVER [] = {
            R.drawable.age_of_ultron,
            R.drawable.avenger_endgame,
            R.drawable.clash_of_the_titan,
            R.drawable.hobbs_shaw,
            R.drawable.lightning_thief,
            R.drawable.mi6,
            R.drawable.percy_jackson,
            R.drawable.skiptrace,
            R.drawable.tokyo_drift,
            R.drawable.wrath_of_the_titan
    };

    private static final String RELEASE_DATE[] = {
            "2015",
            "2019",
            "2010",
            "2019",
            "2010",
            "2018",
            "2013",
            "2016",
            "2006",
            "2012"
    };

    private static final String DURATION[] = {
            "2h 21min",
            "3h 1min",
            "1h 46min",
            "2h 17min",
            "1h 58min",
            "2h 27min",
            "1h 46min",
            "1h 47min",
            "1h 44min",
            "1h 39min"
    };

    private static final double RATING[] = {
            7.3,
            8.4,
            5.8,
            6.4,
            5.9,
            7.7,
            5.8,
            5.7,
            6.0,
            5.7
    };

    private static final String WRITERS[]= {
            "Josh Whedon, Stan Lee",
            "Stan Lee, Jack Kirby",
            "Travis Beacham",
            "Chris Morgan",
            "Rick Riordan",
            "Bruce Geller",
            "Rick Riordan",
            "Jay Longino",
            "Chris Morgan",
            "Dan Mazeau"
    };



    public static ArrayList<MovieModel> getMoviesData(){
        ArrayList<MovieModel> list = new ArrayList<>();
        for(int index = 0; index<TITLE.length; index++){
            MovieModel model = new MovieModel();
            model.setTitle(TITLE[index]);
            model.setGenre(GENRE[index]);
            model.setDetail(DESCRIPTION[index]);
            model.setCover(MOVIE_COVER[index]);
            model.setAuthor(WRITERS[index]);
            model.setRating(RATING[index]);
            model.setDuration(DURATION[index]);
            model.setReleaseDate(RELEASE_DATE[index]);
            list.add(model);
        }
        return list;
    }
}
