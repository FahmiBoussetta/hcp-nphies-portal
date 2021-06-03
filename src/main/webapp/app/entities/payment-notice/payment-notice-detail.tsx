import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payment-notice.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentNoticeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentNoticeDetail = (props: IPaymentNoticeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paymentNoticeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paymentNoticeDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.paymentNotice.detail.title">PaymentNotice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.guid}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.parsed}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.identifier}</dd>
          <dt>
            <span id="paymentDate">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentDate">Payment Date</Translate>
            </span>
          </dt>
          <dd>
            {paymentNoticeEntity.paymentDate ? (
              <TextFormat value={paymentNoticeEntity.paymentDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="amount">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.amount}</dd>
          <dt>
            <span id="paymentStatus">
              <Translate contentKey="hcpNphiesPortalApp.paymentNotice.paymentStatus">Payment Status</Translate>
            </span>
          </dt>
          <dd>{paymentNoticeEntity.paymentStatus}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.paymentNotice.payment">Payment</Translate>
          </dt>
          <dd>{paymentNoticeEntity.payment ? paymentNoticeEntity.payment.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment-notice" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment-notice/${paymentNoticeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ paymentNotice }: IRootState) => ({
  paymentNoticeEntity: paymentNotice.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentNoticeDetail);
