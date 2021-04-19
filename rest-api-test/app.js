const express = require("express"),
  app = express(),
  mongoose = require("mongoose"),
  bcrypt = require("bcrypt"),
  bodyParser = require("body-parser");
  jwt = require("jsonwebtoken");
  http = require ("http");
  dotenv = require('dotenv');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

//using port 3000 - is hidden in our .env file 
dotenv.config();

//start server
const server = http.createServer(app);
server.listen(process.env.PORT);
server.on('listening', ()  => {
  console.log('Server is running & listening on port', process.env.PORT)
}); 

// mongo connection'
mongoose.connect(process.env.MONGO_DB, {
  useNewUrlParser: true,
  useUnifiedTopology: true
})

//will print string to tell us mongo connection status 
mongoose.connection.on('connected', () => {
  console.log('Mongo DB has connected succesfully')
})
mongoose.connection.on('reconnected', () => {
  console.log('Mongo DB has reconnected')
})
mongoose.connection.on('error', error => {
  console.log('Mongo DB connection has an error', error)
  mongoose.disconnect()
})
mongoose.connection.on('disconnected', () => {
  console.log('Mongo DB connection is disconnected')
})

const saltRounds = 10;

const userSchema = new mongoose.Schema({
  username: String,
  email: String, 
  password: String,
  contacts: [contactsSchema],
  joined: { type: Date, default: Date.now },
});


const contactsSchema = new mongoose.Schema({
  contacts: String
});

const User = mongoose.model("user", userSchema);

// Testing get users
/* app.get("/users", function (req, res) {
	User.find({}, function (err, users) {
		
		var a = {}
		
		if (err) {
			res.send("Something went wrong");
			next();
		}
		console.log(Object.keys(a))
		res.json(users);
	});
  }) */


app.get('/users', function(req, res, next) {
        User.find({},'username',function(err, username) {
        res.json(username);
		console.log(username);
      });
    });


//ROUTES to Get Registration and Login Users
app.get("/", (req, res) => {
  res.send("hey your in the routes");
});

app.post("/register", async (req, res) => {
  console.log(req.body);
  try {
    const hashedPwd = await bcrypt.hash(req.body.password, saltRounds);
    const insertResult = await User.create({
      username: req.body.username,
      email: req.body.email,
      password: hashedPwd,
    });
    res.send(insertResult);
  } catch (error) {
    console.log(error);
    res.status(500).send("Internal Server error Occured");
  }
});

app.post("/login", async (req, res) => {
  try {
    const user = await User.findOne({ username: req.body.username });
    console.log(user);
    if (user) {
      const cmp = await bcrypt.compare(req.body.password, user.password);
      if (cmp) {
        //res.send("Auth Successful");
        jwt.sign({user}, 'secretkey', { expiresIn: '30s' }, (err, token) => {
          res.json({
            token
          });
        });

      } else {
        res.send("Wrong username or password.");
      }
    } else {
      res.send("Wrong username or password.");
    }
  } catch (error) {
    console.log(error);
    res.status(500).send("Internal Server error Occured");
  }
});

//doesnt work just place holder - should allow user to change password 
app.patch('/:userId', (req, res, next) => {
  res.status(200).json({
      message: 'Updated Id!'
  });
});

//doesnt work just place holder - should delete user by id 
app.delete('/:userId', (req, res, next) => {
  res.status(200).json({
      message: 'Deleted id!'
  });
});

