import React from  'react';
import ProbaTable from './ProbaTable.js'
import {DeleteProba, AddProba, GetProbe,UpdateProba} from './utils/rest-calls'
import ProbaForm from "./ProbaForm";
import ProbaForm2 from "./ProbaForm2";
import './ProbaApp.css'

class ProbaApp extends React.Component{
    constructor(props){
        super(props);
        this.state={probe:[{"idd":1,"nume":"inot","cod_arb":1}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
            updateFunc:this.updateFunc.bind(this),
        }
        console.log('ProbApp constructor')
    }

    addFunc(proba){
        console.log('inside add Func '+proba);
        AddProba(proba)
            .then(res=> GetProbe())
            .then(probe=>this.setState({probe}))
            .catch(erorr=>console.log('eroare add ',erorr));
    }


    deleteFunc(id){
        console.log('inside deleteFunc '+id);
        DeleteProba(id)
            .then(res=> GetProbe())
            .then(probe=>this.setState({probe}))
            .catch(error=>console.log('eroare delete', error));
    }
    updateFunc(id,proba){
        console.log('inside update Func '+id+proba);
        UpdateProba(id,proba)
            .then(res=> GetProbe())
            .then(probe=>this.setState({probe}))
            .catch(error=>console.log('eroare modif', error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetProbe().then(probe=>this.setState({probe}));
    }

    render(){
        return(
            <div className="ProbaApp">
                <h3 id="titlu">Probe Triatlon</h3>
                <br/>
                <br/>
                <ProbaForm id="form1" addFunc={this.state.addFunc}/>
                <ProbaTable probe={this.state.probe} deleteFunc={this.state.deleteFunc}/>
                <ProbaForm2  id="form2" updateFunc={this.state.updateFunc}/>
            </div>
        );
    }
}

export default ProbaApp;