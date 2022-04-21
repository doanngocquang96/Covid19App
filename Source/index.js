const express = require("express");
const bodyParser = require("body-parser");
const crypto = require("crypto");
const MongoClient = require('mongodb').MongoClient;
const { lutimes } = require("fs");
const objectID = require('mongodb').ObjectId;

//google account login mongodb cloud https://account.mongodb.com/account/login?signedOut=true
// qncvapp@gmail.com
// P@ssw0rdapp


//funcs support password
var genRamdomString = (length) => {

    return crypto.randomBytes(Math.ceil(length/2))
    .toString('hex')
    .slice(0, length);
}

var sha512 = (password, salt) => {
    let hash = crypto.createHmac('sha512', salt);
    hash.update(password);
    let value = hash.digest('hex');
    return{
        salt: salt,
        passwordHash: value
    }
}

function saltHashPassword(userPassword) {
    let salt = genRamdomString(16);
    let passwordData = sha512(userPassword, salt);
    return passwordData;
}
function checkHashPassword(userPassword, salt){
    let passwordData = sha512(userPassword, salt);
    return passwordData;
}

//create express server
let app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

//connect mongoDb
// const url = 'mongodb://localhost:27017';
const url = "mongodb+srv://admindb:fsSzeZDzW7F5EB7a@cluster0.izxfs.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

MongoClient.connect(url, { useNewUrlParser: true, useUnifiedTopology: true }, function(err, client){
    if(err)
        console.log("Unable to connect to the MongoDb server.Error", err);
    else{

        //phan anh from user

        app.post('/qncv/phananh',(req, res, next) => {

            let reqData = req.body;
            let reqDataJson = JSON.parse(reqData.phananh);
            let email = reqDataJson.email;
            
            console.log("data Phản ánh:", reqDataJson);
            
            let db = client.db('qncvdb');

            //check email exists
            db.collection('useraccounts')
            .find({'email': email}).count(function(err, number){
               if(number != 0){
                   
                   db.collection('phananh').insertOne(reqDataJson, function(err, result){
                    if(err || !result){
                        console.log('An error in database')
                        return res.status(400).json({error: 'An error in database'});
                    
                    }else{
                        console.log('Callback successful')
                        res.status(200).json('Callback successfull');
                    }
                    
                    
                })
                }else{
                   console.log('Email not exists');
                   return res.status(400).json({error: 'Email not exists. Error'});
               }

           })


        })
        
        //kbyt user

        app.post('/qncv/kbyt',(req, res, next) => {

            let reqData = req.body;
            let reqDataJson = JSON.parse(reqData.kbyt);
            let email = reqDataJson.email;
            
            console.log("data KBYT:", reqDataJson);
            
            let db = client.db('qncvdb');

            //check email exists
            db.collection('useraccounts')
            .find({'email': email}).count(function(err, number){
               if(number != 0){
                   
                   db.collection('kbyt').insertOne(reqDataJson, function(err, result){
                    if(err || !result){
                        console.log('An error in database')
                        return res.status(400).json({error: 'An error in database'});
                    
                    }else{
                        console.log('declare successful')
                        res.status(200).json('Declare successfull');
                    }
                    
                    
                })
                }else{
                   console.log('Email not exists');
                   return res.status(400).json({error: 'Email not exists. Error'});
               }

           })


        })

        //add More info User

        app.post('/qncv/addmoreinfo', (req, res, next) => {
            let reqData = req.body;
            let email = reqData.email,
            fullName = reqData.fullName,
            sex = reqData.sex,
            birth = reqData.birth,
            idCard = reqData.idCard,
            idBHYT = reqData.idBHYT,
            national = reqData.national,
            address = reqData.address,
            numPhone = reqData.numPhone,
            vaccine = reqData.vaccine;

            console.log("add more info from device", email, fullName, sex, birth, idCard, idBHYT, national, address, numPhone, vaccine)
          
            let db = client.db('qncvdb');

             //check email exists
             db.collection('useraccounts')
             .find({'email': email}).count(function(err, number){
                if(number != 0){
                    
                    db.collection('useraccounts').update(
                        {email: email},
                        {$set: { 
                            'isinfo': true,
                            'fullname': fullName,
                            'sex': sex,
                            'birth': birth,
                            'idcard': idCard,
                            'idBHYT': idBHYT,
                            'national': national,
                            'address': address,
                            'numphone': numPhone,
                            'vaccine': vaccine} },
                        function(err, result){
                        if(err || !result){
                            console.log('An error in database')
                            return res.status(400).json({error: 'An error save on database'});
                        }else{
                            console.log('Update successful')
                            res.status(200).json('Update successful');
                        }
                        
                    })
                }else{
                    console.log('Email not exists');
                    return res.status(400).json({error: 'Email not exists. Error'});
                }

            })

        })

        //Register
        app.post('/qncv/register', (req, res, next) => {
            let reqData = req.body;
            userPassword = reqData.password,
            hashData = saltHashPassword(userPassword),
            password = hashData.passwordHash,
            salt = hashData.salt,
            name = reqData.name,
            email = reqData.email;

            console.log("Register from device", name, email, userPassword)
            let insertJson = {
                'email': email,
                'password': password,
                'name': name,
                'isinfo': false,
                'salt': salt,
                'created' : new Date()
            } 
            let db = client.db('qncvdb');

            //check email exists
            db.collection('useraccounts')
            .find({'email': email}).count(function(err, number){
                if(number != 0){
                    console.log('Email already exists');
                    return res.status(400).json({error: 'Email already exists. Error'});
                }
                else{

                    //insert info user to data
                    db.collection('useraccounts')
                    .insertOne(insertJson, function(err, result){
                        if(err || !result){
                            console.log('An error in database')
                            return res.status(401).json({error: 'An error in database'});
                        
                        }else{
                            console.log('Registration successful')
                            res.status(200).json('Registration successfull');
                        }
                        
                        
                    })
                }
            })
        })

        //login
        app.post('/qncv/login', (req, res, next) => {
            let reqData = req.body;

            let email = reqData.email,
            userPassword = reqData.password;
            console.log("Login from device", email, userPassword)

            let db = client.db('qncvdb');

            //check exists email
            db.collection('useraccounts')
            .find({'email': email}).count(function(err, number){
                if(number == 0){
                    console.log('Email not exists');
                    return res.status(400).json({error: 'Email not exists'});         
                    
                } else {
                    //find and compare password
                    db.collection('useraccounts')
                    .findOne({'email': email}, function(err, result){
                        if(err || !result){
                            console.log('User not found. Error');
                            return res.status(401).json({error: 'User not found. Error'});
                           
                        } else {
                            let salt = result.salt;
                            let hash_password = checkHashPassword(userPassword, salt).passwordHash;
                            let root_password = result.password;
                        
                            if(hash_password == root_password){
                                result.password = "hidden";
                                result.msg = "Login succcessful";
                                console.log('Login succcessful', result);
                                return res.status(200).json(result);
                                
                            } else {
                                console.log('Password is incorrect. Error');
                                
                                return res.status(402).json({error: 'Password is incorrect. Error'});
                            }

                        }
                            
                    })
                }
            })
        })

        //get info app
        app.get("/qncv/info", async (req, res, next) => {
            return res.status(200).json({
                version: '1.0',
                author: "nDs",
                website: "https://ndscoop.github.io/ndsappbeta",
                support: "nds.tphcm@gmail.com",
                more: "Tp.Ho Chi Minh" 
            });
            
        });

        //start web server
        app.listen(9152, () => {
            console.log('Connected to MongoDb server, WebService running on port 9152');
    })
    }
   
})


  