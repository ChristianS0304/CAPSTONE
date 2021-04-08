const http = require ("http");
const express = require('express');
const morgan = require("morgan");
const cors = require ("cors");
const app = express();
const mongoose = require('mongoose');

//routes pushes traffic to routes 
const userRouter = require('./routes/user')

//using port 3000
const port = 3000;

//tells express you want all data in json 
app.use(express.json())
//allows us to get nested json 
app.use(express.urlencoded({extended: true}))

app.use(morgan('dev'))
app.use(cors());

app.use('/user', userRouter)


const server = http.createServer(app);
server.listen(port);
server.on('listening', ()  => {
	console.log('listening on port', port)
}); 

