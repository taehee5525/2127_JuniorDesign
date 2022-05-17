import React, { Component } from 'react';
import { AppBar, Button, Stack } from "@react-native-material/core";
import { StyleSheet, Image, View, Text, TextInput} from "react-native";
import { useNavigation } from '@react-navigation/native';

const SignUp = () => {
    const navigation = useNavigation();
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
            />
        <TextInput style= {styles.input}
            placeholder = "Email"
            keyboardType = "default"
            />
        <TextInput style = {styles.input}
            placeholder = "Password"
            keyboardType = "default"
            />
        <TextInput style = {styles.input}
            placeholder = "Confirm Password"
            keyboardType = "default"
            />

        <Text style = {styles.condition}
            onPress = {() => navigation.navigate('Terms') } >
            {"Terms and Condition"}
        </Text>

        <Stack fill center spacing = { 20 }>
            <Button style = {styles.button}
                title = "Sign Up"
                color = "#B3A369"
                tintColor = "white" />
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