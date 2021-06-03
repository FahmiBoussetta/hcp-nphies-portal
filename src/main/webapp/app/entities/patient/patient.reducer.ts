import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPatient, defaultValue } from 'app/shared/model/patient.model';

export const ACTION_TYPES = {
  FETCH_PATIENT_LIST: 'patient/FETCH_PATIENT_LIST',
  FETCH_PATIENT: 'patient/FETCH_PATIENT',
  CREATE_PATIENT: 'patient/CREATE_PATIENT',
  UPDATE_PATIENT: 'patient/UPDATE_PATIENT',
  PARTIAL_UPDATE_PATIENT: 'patient/PARTIAL_UPDATE_PATIENT',
  DELETE_PATIENT: 'patient/DELETE_PATIENT',
  RESET: 'patient/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPatient>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PatientState = Readonly<typeof initialState>;

// Reducer

export default (state: PatientState = initialState, action): PatientState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PATIENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PATIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PATIENT):
    case REQUEST(ACTION_TYPES.UPDATE_PATIENT):
    case REQUEST(ACTION_TYPES.DELETE_PATIENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PATIENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PATIENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PATIENT):
    case FAILURE(ACTION_TYPES.CREATE_PATIENT):
    case FAILURE(ACTION_TYPES.UPDATE_PATIENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PATIENT):
    case FAILURE(ACTION_TYPES.DELETE_PATIENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATIENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATIENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PATIENT):
    case SUCCESS(ACTION_TYPES.UPDATE_PATIENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PATIENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PATIENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/patients';

// Actions

export const getEntities: ICrudGetAllAction<IPatient> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PATIENT_LIST,
  payload: axios.get<IPatient>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPatient> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PATIENT,
    payload: axios.get<IPatient>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPatient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PATIENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPatient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PATIENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPatient> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PATIENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPatient> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PATIENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
