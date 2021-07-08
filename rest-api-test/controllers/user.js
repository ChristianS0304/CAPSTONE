// utils
const makeValidation = require('@withvoid/make-validation');

const userController = {

getAll: (req, res, next) => {
    return res.status(200).json({success: true, message: "hello postman"})
},

getById: (req, res, next) => {
    const { id } = req.params
    return res.status(200).json({success: true, message: "hello postman I am ${id}" })
 },

 createUser: (req, res, next) => {
    const {firstName, lastName, email } = req.body;

    const result = makeValidation(types => {
        return {
          payload: req.body,
          checks: {
            firstName: { type: types.string, options: { empty: false } },
            lastName: { type: types.string, options: { empty: false } },
            email: { type: types.string, options: { empty: false } },
          }
        }
      })
      if (!result.success) {
        return res.status(400).json({ ...result })
      }
  
      // only execute everything below once I am sure that the data is
      // correct
      const userPayload = {
        firstName,
        lastName,
        email,
      }
      return res.status(200).json({
        success: true,
        message: `Job well done`,
        data: userPayload
      })
    },
    
  updateUser: (req, res, next) => {
    const { id } = req.params
    return res
      .status(200)
      .json({ success: true, message: `Updated user id: ${id}` })
  },
  deleteUser: (req, res, next) => {
    const { id } = req.params
    return res
      .status(200)
      .json({ success: true, message: `Delete user id: ${id}` })
  }
}
module.exports = userController; 