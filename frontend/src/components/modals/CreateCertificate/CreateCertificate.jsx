import React, { useEffect, useState } from 'react'
import { Form, Modal, Button } from 'react-bootstrap'
import { createCertificateRequest } from '../../../service/api';
import { updateCertificate, loadCertificatesRequest } from '../../../service/api';
import { useDispatch } from 'react-redux';
import loadCertificatesAction from '../../../store/actions/LoadCertificatesAction';


function CreateCertificate({ show, onHide, certificate, page, size }) {
    const dispatch = useDispatch();

    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [durationInDays, setDurationInDays] = useState('');
    const [showErrorName, setShowErrorName] = useState(false);
    const [errorMessageName, setErrorMessageName] = useState('');
    const [showErrorDescription, setShowErrorDescription] = useState(false);
    const [errorMessageDescription, setErrorMessageDescription] = useState('');
    const [showErrorPrice, setShowErrorPrice] = useState(false);
    const [errorMessagePrice, setErrorMessagePrice] = useState('');
    const [showErrorDuration, setShowErrorDuration] = useState(false);
    const [errorMessageDuration, setErrorMessageDuration] = useState('');
    const [error, setError] = useState('');
    const [showError, setShowError] = useState(false);
    const [tags, setTags] = useState([]);
    const [input, setInput] = useState('');
    const [isValidName, setIsValidName] = useState(false)
    const [isValidDescription, setIsValidDescription] = useState(false)
    const [isValidPrice, setIsValidPrice] = useState(false)
    const [isValidDuration, setIsValidDuration] = useState(false)


    useEffect(() => {
        if (certificate && certificate.tags.length > 0) {
            let tagsList = []
            certificate.tags.map(tag => {
                tagsList.push(tag.name)
                setTags(tagsList);
            })
        }

        if (certificate) {
            setName(certificate.name);
            setDescription(certificate.description);
            setPrice(certificate.price);
            setDurationInDays(certificate.durationInDays)
        }
    }, []);

    function save() {
        setShowError(false);
        setError('')
        let tagsList = []
        tags.map((tag) => { tagsList.push({ 'name': tag }) })

        if (isValidName && isValidDescription && isValidPrice && isValidDuration) {
            setShowError(false);
            setError('')
            let certificateDto = {
                'name': name,
                'description': description,
                'price': price,
                'durationInDays': durationInDays,
                'tags': tagsList
            }

            if (certificate) {
                updateCertificate(certificateDto, certificate.id)
                    .then((response) => {
                        loadCertificatesRequest({
                            params: {
                                'page': page,
                                'size': size
                            }
                        }).then(response => {
                            dispatch(loadCertificatesAction(response.data.giftCertificateDtoList))
                        })
                        onHide();
                    }).catch((error) => {
                        console.log(error.response)
                    })

            } else {
                createCertificateRequest(certificateDto)
                    .then(() => {
                        onHide();
                    }).catch((error) => { console.log(error.response) })
            }
        } else {
            setShowError(true);
            setError('Check data');
        }
    }

    const onChange = (e) => {
        const { value } = e.target;
        setInput(value);
    }
    const onKeyDown = (e) => {
        const { key } = e;
        const trimmedInput = input.trim();

        if (key === ',' && trimmedInput.length && !tags.includes(trimmedInput)) {
            e.preventDefault();
            setTags(prevState => [...prevState, trimmedInput]);
            setInput('');
        }
    }
    const deleteTag = (index, e) => {
        e.preventDefault();
        setTags(tags => tags.filter((tag, i) => i !== index))
    }


    const validateName = (e) => {
        e.preventDefault();
        let inputName = e.target.value;
        setName(inputName);
        if (name.length > 6 && name.length < 30) {
            setShowErrorName(false);
            setErrorMessageName('')
            setIsValidName(true)
        } else {
            setIsValidName(false)
            setShowErrorName(true);
            setErrorMessageName('Name have to be less than 6 and greater than 30 characters');

        }
    }

    const validateDescription = (e) => {
        e.preventDefault();
        let inputDescription = e.target.value;
        setDescription(inputDescription);
        if (description.length > 12 && description.length < 1000) {
            setShowErrorDescription(false);
            setErrorMessageDescription('')
            setIsValidDescription(true)
        } else {
            setIsValidDescription(false)
            setShowErrorDescription(true);
            setErrorMessageDescription('Description field must not be less than 12 and greater than 1000 characters')

        }

    }

    const validatePrice = (e) => {
        e.preventDefault();
        let inputPrice = e.target.value;
        setPrice(inputPrice);
        if (price > 0 && (isInt(parseInt(price)) || isFloat(parseFloat(price)))) {
            setShowErrorPrice(false);
            setErrorMessagePrice('')
            setIsValidPrice(true)
        } else {
            setIsValidPrice(false)
            setShowErrorPrice(true);
            setErrorMessagePrice('Price must be a number or float and be greater than 0')
        }
    }

    const validateDuration = (e) => {
        e.preventDefault();
        let inputDuration = e.target.value;
        setDurationInDays(inputDuration);
        if ((durationInDays >= 0) && isInt(parseInt(durationInDays))) {
            setShowErrorDuration(false);
            setShowErrorDuration('')
            setIsValidDuration(true)
        } else {
            setIsValidDuration(false)
            setShowErrorDuration(true)
            setErrorMessageDuration('Duration must be a number');
        }

    }

    function isInt(n) {
        return (Number(n) === n) && (n % 1 === 0);
    }

    function isFloat(n) {
        return Number(n) === n && n % 1 !== 0;
    }


    return (
        <Modal
            show={show}
            onHide={onHide}
            size="lg"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    {!certificate && 'Add new certificate'}
                    {certificate && 'Edit certificate'}
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form>
                    {showError && <span>{error}</span>}
                    <Form.Control defaultValue={name} type="text" placeholder="Name" onChange={e => { validateName(e) }} />
                    <br />
                    {showErrorName && <p style={{ color: 'red', 'font-size': '12px', 'margin-top': '-10px', 'margin-bottom': '-10px' }}>{errorMessageName}</p>}<br />
                    <Form.Control defaultValue={description} type="text" placeholder="Description" onChange={e => { validateDescription(e) }} /><br />
                    {showErrorDescription && <p style={{ color: 'red', 'font-size': '12px', 'margin-top': '-10px', 'margin-bottom': '-10px' }}>{errorMessageDescription}</p>}<br />
                    <Form.Control value={price} type="text" placeholder="Price" onChange={(e) => { validatePrice(e) }} /><br />
                    {showErrorPrice && <p style={{ color: 'red', 'font-size': '12px', 'margin-top': '-10px', 'margin-bottom': '-10px' }}>{errorMessagePrice}</p>}<br />
                    <Form.Control value={durationInDays} type="text" placeholder="Duration in days" onChange={(e) => { validateDuration(e) }} />
                    {showErrorDuration && <p style={{ color: 'red', 'font-size': '12px', 'margin-top': 'px', 'margin-bottom': '5px' }}>{errorMessageDuration}</p>}<br />
                    <br /> <Form.Control
                        value={input}
                        placeholder="Enter a tag"
                        onKeyDown={onKeyDown}
                        onChange={onChange}
                    /><br />
                    <div className="tagsClass">
                        {tags.map((tag, index) =>
                            <div style={{ display: 'inline-block' }} key={index} className="tag">{tag}
                                <button onClick={(e) => deleteTag(index, e)}>x</button>
                            </div>
                        )}
                    </div>
                </Form>

            </Modal.Body>
            <Modal.Footer>
                <Button variant="outline-success" onClick={save} >Save</Button>
                <Button variant="outline-danger" onClick={onHide}>Cancel</Button>
            </Modal.Footer>
        </Modal >
    )
}


export default CreateCertificate