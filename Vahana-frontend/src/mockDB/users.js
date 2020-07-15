const users = [
    {
        username: "abhilash.mishra003@gmail.com",
        password: "abhilash.mishra003@gmail.com"
    },
    {
        username: "mehariaabhishek@gmail.com",
        password: "mehariaabhishek@gmail.com"
    },
    {
        username: "samarth.mohanty5@gmail.com",
        password: "samarth.mohanty5@gmail.com"
    },
    {
        username: "bislara",
        password: "bislara"
    },
    {
        username: "a",
        password: "a",
        station: {
            _id:"mastercanteenrailwaystation_train",
            name: "Master Canteen Railway Station",
            type: "Train"
        }
    }
]

const getUserByUsername=(uname)=>{
    const foundUser = users.find((user)=>user.username===uname);
    return foundUser;
}

export {users, getUserByUsername};