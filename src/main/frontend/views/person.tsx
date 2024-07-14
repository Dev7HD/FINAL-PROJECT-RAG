import {AutoCrud} from "@vaadin/hilla-react-crud";
import {PersonService} from "Frontend/generated/endpoints";
import PersonModel from "Frontend/generated/ma/dev7hd/finalexamrag/entities/PersonModel";

const Person = () => {
    return(
        <AutoCrud service={PersonService} model={PersonModel} />
    );
}

export default Person;