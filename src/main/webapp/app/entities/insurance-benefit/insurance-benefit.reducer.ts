import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInsuranceBenefit, defaultValue } from 'app/shared/model/insurance-benefit.model';

export const ACTION_TYPES = {
  FETCH_INSURANCEBENEFIT_LIST: 'insuranceBenefit/FETCH_INSURANCEBENEFIT_LIST',
  FETCH_INSURANCEBENEFIT: 'insuranceBenefit/FETCH_INSURANCEBENEFIT',
  CREATE_INSURANCEBENEFIT: 'insuranceBenefit/CREATE_INSURANCEBENEFIT',
  UPDATE_INSURANCEBENEFIT: 'insuranceBenefit/UPDATE_INSURANCEBENEFIT',
  PARTIAL_UPDATE_INSURANCEBENEFIT: 'insuranceBenefit/PARTIAL_UPDATE_INSURANCEBENEFIT',
  DELETE_INSURANCEBENEFIT: 'insuranceBenefit/DELETE_INSURANCEBENEFIT',
  RESET: 'insuranceBenefit/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInsuranceBenefit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type InsuranceBenefitState = Readonly<typeof initialState>;

// Reducer

export default (state: InsuranceBenefitState = initialState, action): InsuranceBenefitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSURANCEBENEFIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSURANCEBENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_INSURANCEBENEFIT):
    case REQUEST(ACTION_TYPES.UPDATE_INSURANCEBENEFIT):
    case REQUEST(ACTION_TYPES.DELETE_INSURANCEBENEFIT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_INSURANCEBENEFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_INSURANCEBENEFIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSURANCEBENEFIT):
    case FAILURE(ACTION_TYPES.CREATE_INSURANCEBENEFIT):
    case FAILURE(ACTION_TYPES.UPDATE_INSURANCEBENEFIT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_INSURANCEBENEFIT):
    case FAILURE(ACTION_TYPES.DELETE_INSURANCEBENEFIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSURANCEBENEFIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSURANCEBENEFIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSURANCEBENEFIT):
    case SUCCESS(ACTION_TYPES.UPDATE_INSURANCEBENEFIT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_INSURANCEBENEFIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSURANCEBENEFIT):
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

const apiUrl = 'api/insurance-benefits';

// Actions

export const getEntities: ICrudGetAllAction<IInsuranceBenefit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INSURANCEBENEFIT_LIST,
  payload: axios.get<IInsuranceBenefit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IInsuranceBenefit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSURANCEBENEFIT,
    payload: axios.get<IInsuranceBenefit>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IInsuranceBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSURANCEBENEFIT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInsuranceBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSURANCEBENEFIT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IInsuranceBenefit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_INSURANCEBENEFIT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInsuranceBenefit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSURANCEBENEFIT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
