const express = require('express');
const router = express.Router();

//controller for user 
const userController = require('../controllers/user');


// recived from the app.js, routes all the traffic to the controller 
router
    .get('/', userController.getAll)
    .get('/:id', userController.getById)
    .post('/', userController.createUser)
    .delete('/id;', userController.deleteUser)

  
module.exports = router
