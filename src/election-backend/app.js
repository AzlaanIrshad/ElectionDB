const express = require('express');
const cors = require('cors');
const { Sequelize, DataTypes } = require('sequelize');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const app = express();
const PORT = 8080;
const SECRET_KEY = 'your_secret_key';

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

const User = sequelize.define('User', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    password: {
        type: DataTypes.STRING,
        allowNull: false
    }
}, {
    tableName: 'user',
    timestamps: false
});

// Sync the database and create
sequelize.sync({ force: true })
    .then(async () => {
        console.log('Database & tables created!');

        // Insert dummy candidate data
        await Candidate.bulkCreate([
            { name: 'Azlaan Sahan', party: 'Stink Party' },
            { name: 'Erdem Berk Irshad', party: 'Cute Party' },
            { name: 'Rahim Pookie', party: 'Kaboem Party' }
        ]);

        const hashedPassword = await bcrypt.hash('test', 10);
        await User.create({
            email: 'test@test.nl',
            password: hashedPassword
        });

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

// Login route
app.post('/api/login', async (req, res) => {
    const { email, password } = req.body;

    try {
        // Check if the user exists
        const user = await User.findOne({ where: { email } });

        if (!user) {
            return res.status(401).json({ message: 'Invalid email or password' });
        }

        // Compare passwords
        const isPasswordValid = await bcrypt.compare(password, user.password);

        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Invalid email or password' });
        }

        // Generate JWT token
        const token = jwt.sign({ userId: user.id }, SECRET_KEY, { expiresIn: '1h' });

        // Respond with token
        res.json({ message: 'Login successful', token });
    } catch (error) {
        console.error('Login error:', error);
        res.status(500).json({ message: 'Internal server error' });
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
