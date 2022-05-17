import React, { Component, useState, createRef} from 'react';
import { AppBar, Button, Stack } from "@react-native-material/core";
import { StyleSheet, Image, View, Text, TextInput} from "react-native";
import { useNavigation } from '@react-navigation/native';

const SignUp = () => {

    const navigation = useNavigation();
    const [UserName, setUserName] = useState('');
    const [Email, setEmail] = useState('');
    const [Password, setPassword] = useState('');
    const [Confirm, setConfirm] = useState('');

    const handleSignUpButton = () => {
        const emailRegex = new RegExp("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$");
        const strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})");
        if (!UserName || !Email || !Password || !Confirm) {
            alert('One or more required fields are missing\nPlease check again');
            return;
        }

        if (!emailRegex.test(Email)) {
            alert('Email is not in correct format.\nPlease check again');
            return;
        }

        if (!strongRegex.test(Password)) {
            alert('Your password is not strong enough.\n'+
                    'The password must contain\n' +
                    '1 lowercase,\n' +
                    '1 uppercase,\n' +
                    '1 numeric character,\n' +
                    '1 special character,\n' +
                    'and must be 8 characters or longer');
            return;
        }

        if (Password != Confirm) {
            alert('Passwords are not matching');
            return;
        }
        alert('Practiceeeee');
    }
    return  (
    <>
        <AppBar titleStyle = {styles.titles}
                title = "Create Account"
                color = "#F9F9F9"
                centerTitle = "True"
        />
        <View style = {styles.container} >
            <Image source = {
            require('./images/bgimage.png')}
                style = {{ width: 100, height: 100 }} />
            <Text style = {styles.centerText} >
                {"Create account"}
                {"\n"}
            </Text>
        </View>

        <TextInput style= {styles.input}
            placeholder = "Name"
            keyboardType = "default"
            onChangeText = {(UserName) => setUserName(UserName)}
            />
        <TextInput style= {styles.input}
            placeholder = "Email"
            keyboardType = "default"
            onChangeText = {(Email) => setEmail(Email)}
            />
        <TextInput style = {styles.input}
            placeholder = "Password"
            keyboardType = "default"
            secureTextEntry = { true }
            onChangeText = {(Password) => setPassword(Password)}
            />
        <TextInput style = {styles.input}
            placeholder = "Confirm Password"
            keyboardType = "default"
            secureTextEntry =  { true }
            onChangeText = {(Confirm) => setConfirm(Confirm)}
            />

        <Text style = {styles.condition}
            onPress = {() => navigation.navigate('Terms') } >
            {"Terms and Condition"}
        </Text>

        <Stack fill center spacing = { 20 }>
            <Button style = {styles.button}
                title = "Sign Up"
                color = "#B3A369"
                tintColor = "white"
                onPress = {handleSignUpButton} />
            <Text style = {{ color: 'black' }} >
                {"Already have an account?"}
            <Text style = {{ color : 'blue' }}
                onPress = {() => navigation.navigate('SignIn') }>
                {"  Sign In"}
             </Text>
             </Text>
        </Stack>

    </>
    );
};



const styles = StyleSheet.create ( {
    titles: {
        color: "black",
        fontSize: 18,
        fontWeight: "bold",
        fontFamily: 'System'
    },

    container: {
        alignItems: 'center'
    },

    centerText: {
        fontFamily: 'System',
        fontWeight: 'bold',
        color: 'black',
        fontSize: 40,
        fontStyle: 'italic'
    },

    input: {
        height: 40,
        margin: 10,
        borderBottomWidth: 0.5
    },

    condition: {
        marginTop: 15,
        textAlign: 'center',
        flex: 0.5,
        color: 'blue',
        fontWeight: 'bold',
        fontFamily: 'System'
    },

    button: {
        width: '80%',
        marginTop: 0
    },


});

export default SignUp;