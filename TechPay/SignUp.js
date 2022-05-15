import React, { Component } from 'react';
import { AppBar } from "@react-native-material/core";
import { StyleSheet, Image, View, Text, TextInput } from "react-native";

const SignUp = () => {
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
            {"\n"}
        </Text>
    </View>

        <TextInput style= {styles.input}
            placeholder = "Email"
            keyboardType = "default"
            />
        <TextInput style = {styles.input}
            placeholder = "Password"
            keyboardType = "default"
            />
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
    }
});

export default SignUp;