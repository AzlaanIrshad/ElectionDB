const express = require('express');
const cors = require('cors');
const { Sequelize, DataTypes } = require('sequelize');

const app = express();
const PORT = 8080;

app.use(cors());

// Middleware to parse JSON requests
app.use(express.json());

const sequelize = new Sequelize('sm3test', 'root', '', {
    host: 'localhost',
    dialect: 'mysql',
    logging: console.log,
});

// Candidate model
const Candidate = sequelize.define('Candidate', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false
    },
    party: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    tableName: 'candidate',
    timestamps: false
});

// Sync the database and create
sequelize.sync({ force: true })
    .then(async () => {
        console.log('Database & tables created!');

        // Insert dummy data
        await Candidate.bulkCreate([
            { name: 'Azlaan Sahan', party: 'Stink Party' },
            { name: 'Erdem Berk Irshad', party: 'Cute Party' },
            { name: 'Rahim Pookie', party: 'Kaboem Party' }
        ]);

        console.log('Dummy data inserted!');
    })
    .catch(err => {
        console.error('Error syncing the database:', err);
    });

// Test connection
sequelize.authenticate()
    .then(() => {
        console.log('Connection to the database has been established successfully.');
    })
    .catch(err => {
        console.error('Unable to connect to the database:', err);
    });

// Get all candidates from the db
app.get('/api/candidates', async (req, res) => {
    try {
        const candidates = await Candidate.findAll();
        res.json(candidates);
    } catch (error) {
        console.error('Error fetching candidates:', error);
        res.status(500).json({ message: 'Error fetching candidates' });
    }
});

// Test
app.get('/api/test', (req, res) => {
    res.send('Backend is working!');
});

// Start
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
