import React, { Component, useState, createRef} from 'react';
import { AppBar, Button, Stack} from "@react-native-material/core";
import { Image, View, StyleSheet, Alert, Text, TextInput} from "react-native";
import { useNavigation } from '@react-navigation/native';

const SignIn = () => {
    const navigation = useNavigation();
    const [Email, setEmail] = useState('');
    const [Password, setPassword] = useState('');

    const handleSignInButton = () => {
        const emailRegex = new RegExp("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$");

        if (!Email || !Password) {
            alert('One or more required fields are missing\nPlease check again');
            return;
        }

        if (!emailRegex.test(Email)) {
            alert('Email is not in correct format.\nPlease check again');
            return;
        }

        alert('Here we go!');
    }
    return  (
    <>
        <AppBar titleStyle = {styles.titles}
                title = "Sign-In"
                color = "#F9F9F9"
                tintColor = "black"
                centerTitle = "True"
        />
        <View style = {styles.container} >
            <Image source = {
            require('./images/bgimage.png')}
                style = {{ width: 100, height: 100 }} />
            <Text style = {styles.centerText} >
                {"Sign In"}
                </Text>
                <Text style = {styles.subTitle} >
                    {"    Welcome back, \n"}
                    {"Sign in to continue\n"}
                </Text>
        </View>

        <TextInput style= {styles.input}
            placeholder = "Email"
            keyboardType = "default"
            onChangeText = {(Email) => setEmail(Email)}
            />
        <TextInput style = {styles.input}
            placeholder = "Password"
            keyboardType = "default"
            onChangeText = {(Password) => setPassword(Password)}
            />

        <Text style = {styles.forgot}
            onPress = {() => navigation.navigate('Forgot') } >
            {"Forgot Password?   "}
        </Text>

        <Stack fill center spacing = { 20 }>
            <Button style = {styles.button}
                title = "Sign In"
                color = "#B3A369"
                tintColor = "white"
                 onPress = {handleSignInButton} />
            <Text> {"──────── Or ──────── "} </Text>
            <Text style = {{ color: 'black' }} >
                {"Don't have an account? \n"}
                <Text style = {{ color : 'blue' }}
                    onPress = {() => navigation.navigate('SignUp') }>
                    {"Create Account"}
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
        alignItems: 'center',
    },

    centerText: {
        fontFamily: 'System',
        fontWeight: 'bold',
        color: 'black',
        fontSize: 40,
        fontStyle: 'italic',
        marginBottom: 10
    },

    input: {
        height: 40,
        margin: 10,
        borderBottomWidth: 0.5
    },

    subTitle: {
        fontFamily: 'System',
        color: 'gray',
        fontSize: 15,
        alignSelf: 'center'
    },

    forgot: {
        textAlign: 'right',
        flex: 0.5,
        color: 'blue',
        fontWeight: 'bold',
        fontFamily: 'System'
    },

    button: {
        width: '80%'
    }


});


export default SignIn;