import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHospitalization, defaultValue } from 'app/shared/model/hospitalization.model';

export const ACTION_TYPES = {
  FETCH_HOSPITALIZATION_LIST: 'hospitalization/FETCH_HOSPITALIZATION_LIST',
  FETCH_HOSPITALIZATION: 'hospitalization/FETCH_HOSPITALIZATION',
  CREATE_HOSPITALIZATION: 'hospitalization/CREATE_HOSPITALIZATION',
  UPDATE_HOSPITALIZATION: 'hospitalization/UPDATE_HOSPITALIZATION',
  PARTIAL_UPDATE_HOSPITALIZATION: 'hospitalization/PARTIAL_UPDATE_HOSPITALIZATION',
  DELETE_HOSPITALIZATION: 'hospitalization/DELETE_HOSPITALIZATION',
  RESET: 'hospitalization/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHospitalization>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type HospitalizationState = Readonly<typeof initialState>;

// Reducer

export default (state: HospitalizationState = initialState, action): HospitalizationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HOSPITALIZATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HOSPITALIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HOSPITALIZATION):
    case REQUEST(ACTION_TYPES.UPDATE_HOSPITALIZATION):
    case REQUEST(ACTION_TYPES.DELETE_HOSPITALIZATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HOSPITALIZATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HOSPITALIZATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HOSPITALIZATION):
    case FAILURE(ACTION_TYPES.CREATE_HOSPITALIZATION):
    case FAILURE(ACTION_TYPES.UPDATE_HOSPITALIZATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HOSPITALIZATION):
    case FAILURE(ACTION_TYPES.DELETE_HOSPITALIZATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOSPITALIZATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HOSPITALIZATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HOSPITALIZATION):
    case SUCCESS(ACTION_TYPES.UPDATE_HOSPITALIZATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HOSPITALIZATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HOSPITALIZATION):
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

const apiUrl = 'api/hospitalizations';

// Actions

export const getEntities: ICrudGetAllAction<IHospitalization> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HOSPITALIZATION_LIST,
  payload: axios.get<IHospitalization>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IHospitalization> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HOSPITALIZATION,
    payload: axios.get<IHospitalization>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHospitalization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HOSPITALIZATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHospitalization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HOSPITALIZATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHospitalization> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HOSPITALIZATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHospitalization> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HOSPITALIZATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
