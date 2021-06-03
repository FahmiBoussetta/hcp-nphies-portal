import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICostToBeneficiaryComponent, defaultValue } from 'app/shared/model/cost-to-beneficiary-component.model';

export const ACTION_TYPES = {
  FETCH_COSTTOBENEFICIARYCOMPONENT_LIST: 'costToBeneficiaryComponent/FETCH_COSTTOBENEFICIARYCOMPONENT_LIST',
  FETCH_COSTTOBENEFICIARYCOMPONENT: 'costToBeneficiaryComponent/FETCH_COSTTOBENEFICIARYCOMPONENT',
  CREATE_COSTTOBENEFICIARYCOMPONENT: 'costToBeneficiaryComponent/CREATE_COSTTOBENEFICIARYCOMPONENT',
  UPDATE_COSTTOBENEFICIARYCOMPONENT: 'costToBeneficiaryComponent/UPDATE_COSTTOBENEFICIARYCOMPONENT',
  PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT: 'costToBeneficiaryComponent/PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT',
  DELETE_COSTTOBENEFICIARYCOMPONENT: 'costToBeneficiaryComponent/DELETE_COSTTOBENEFICIARYCOMPONENT',
  RESET: 'costToBeneficiaryComponent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICostToBeneficiaryComponent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CostToBeneficiaryComponentState = Readonly<typeof initialState>;

// Reducer

export default (state: CostToBeneficiaryComponentState = initialState, action): CostToBeneficiaryComponentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COSTTOBENEFICIARYCOMPONENT):
    case REQUEST(ACTION_TYPES.UPDATE_COSTTOBENEFICIARYCOMPONENT):
    case REQUEST(ACTION_TYPES.DELETE_COSTTOBENEFICIARYCOMPONENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT):
    case FAILURE(ACTION_TYPES.CREATE_COSTTOBENEFICIARYCOMPONENT):
    case FAILURE(ACTION_TYPES.UPDATE_COSTTOBENEFICIARYCOMPONENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT):
    case FAILURE(ACTION_TYPES.DELETE_COSTTOBENEFICIARYCOMPONENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COSTTOBENEFICIARYCOMPONENT):
    case SUCCESS(ACTION_TYPES.UPDATE_COSTTOBENEFICIARYCOMPONENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COSTTOBENEFICIARYCOMPONENT):
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

const apiUrl = 'api/cost-to-beneficiary-components';

// Actions

export const getEntities: ICrudGetAllAction<ICostToBeneficiaryComponent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT_LIST,
  payload: axios.get<ICostToBeneficiaryComponent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICostToBeneficiaryComponent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COSTTOBENEFICIARYCOMPONENT,
    payload: axios.get<ICostToBeneficiaryComponent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICostToBeneficiaryComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COSTTOBENEFICIARYCOMPONENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICostToBeneficiaryComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COSTTOBENEFICIARYCOMPONENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICostToBeneficiaryComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COSTTOBENEFICIARYCOMPONENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICostToBeneficiaryComponent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COSTTOBENEFICIARYCOMPONENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
